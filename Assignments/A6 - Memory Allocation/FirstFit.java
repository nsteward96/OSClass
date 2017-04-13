// Nate Steward
// Finished 4-12-17
// First Fit ISwapper will fit the memory into the first available hole.

import java.util.LinkedList;

public class FirstFit implements ISwapper {
  int m_totalmemory = 0;
  LinkedList<MemNode> memory = new LinkedList<MemNode>();

  public void init(int memSize) {
    int m_totalmemory = memSize;
    memory.add(new MemNode("Hole", m_totalmemory, -1));
  }

  public boolean load(IProcess p, IMemory mem) throws BlueScreenException {
    int processSize = p.getSize();
    int memStart = 0;
    for (int i = 0; i < memory.size(); i++) {
      MemNode block = memory.get(i);
      if (block.getType() == "Hole" && block.getSize() > processSize) {
        int newHoleSize = block.getSize() - processSize;
        MemNode insertedProcess = new MemNode("Process", processSize, p.getId());
        memory.set(i, insertedProcess);
        if (newHoleSize > 0) {
          MemNode newHole = new MemNode("Hole", newHoleSize, -1);
          memory.add(i+1, newHole);
        }
        mem.load(p, memStart, memStart + p.getSize() - 1);
        return true;
      } else {
        memStart += block.getSize();
      }
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
