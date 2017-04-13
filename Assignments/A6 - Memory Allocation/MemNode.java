public class MemNode {
  private String m_type;
  private int m_size;
  private int m_id;

  public MemNode(String type, int size, int id) {
    m_type = type;
    m_size = size;
    m_id = id;
  }

  public String getType() {
    return m_type;
  }
  public int getSize() {
    return m_size;
  }
  public int getId() {
    return m_id;
  }

  public void setType(String type) {
    m_type = type;
  }
  public void setSize(int size) {
    m_size = size;
  }
  public void setId(int id) {
    m_id = id;
  }
}
