// Nate Steward
// Finished 4-12-17
// Best Fit ISwapper will fit the memory into the best position - taking up the
//  smallest hole it can.

import java.util.*;

public class BestFit implements ISwapper {
  int m_totalmemory = 0;
  LinkedList<MemNode> memory = new LinkedList<MemNode>();

  public void init(int memSize) {
    int m_totalmemory = memSize;
    memory.add(new MemNode("Hole", m_totalmemory, -1));
  }

  public boolean load(IProcess p, IMemory mem) throws BlueScreenException {
    List<MemNode> listOfHoles = new ArrayList<MemNode>();
    List<Integer> holeIndexes = new ArrayList<Integer>();
    int processSize = p.getSize();
    int bestIndex = -1;
    for (int i = 0; i < memory.size(); i++) {
      if (memory.get(i).getType() == "Hole") {
        listOfHoles.add(memory.get(i));
        holeIndexes.add(i);
      }
    }
    for (int i = 0; i < listOfHoles.size(); i++) {
      if (listOfHoles.get(i).getSize() >= p.getSize()) {
        if (bestIndex == -1) {
          bestIndex = i;
        } else {
          if (listOfHoles.get(i).getSize() < listOfHoles.get(bestIndex).getSize()) {
            bestIndex = i;
          }
        }
      }
    }
    if (bestIndex != -1) {
      MemNode block = memory.get(holeIndexes.get(bestIndex));
      int newHoleSize = block.getSize() - processSize;
      MemNode insertedProcess = new MemNode("Process", processSize, p.getId());
      memory.set(holeIndexes.get(bestIndex), insertedProcess);
      if (newHoleSize > 0) {
        MemNode newHole = new MemNode("Hole", newHoleSize, -1);
        memory.add(holeIndexes.get(bestIndex)+1, newHole);
      }
      int memStart = 0;
      for (int i = 0; i < holeIndexes.get(bestIndex); i++) {
        memStart += memory.get(i).getSize();
      }
      mem.load(p, memStart, memStart + p.getSize() - 1);
      return true;
    }
    return false;
  }

  public void unload(IProcess p, IMemory mem) throws BlueScreenException {
    for (int i = 0; i < memory.size(); i++) {
      if (memory.get(i).getId() == p.getId()) {
        int size = memory.get(i).getSize();
        memory.remove(i);
        MemNode newHole = new MemNode("Hole", size, -1);
        memory.add(i, newHole);
        mem.unload(p);
        if (i > 1) {
          if (memory.get(i-1).getType() == "Hole" && memory.get(i).getType() == "Hole") { // Combine current and previous hole
            int hole1Size = memory.get(i-1).getSize();
            int hole2Size = memory.get(i).getSize();
            memory.remove(i-1);
            MemNode newHoleBehind = new MemNode("Hole", hole1Size + hole2Size, -1);
            memory.add(i-1, newHoleBehind);
            memory.remove(i);
          }
        }
        if (i < memory.size() - 1) {
          if (memory.get(i+1).getType() == "Hole" && memory.get(i).getType() == "Hole") { // Combine current and forward hole
            int hole1Size = memory.get(i+1).getSize();
            int hole2Size = memory.get(i).getSize();
            memory.remove(i+1);
            MemNode newHoleAhead = new MemNode("Hole", hole1Size + hole2Size, -1);
            memory.add(i+1, newHoleAhead);
            memory.remove(i);
          }
        }
      }
    }
  }
}
