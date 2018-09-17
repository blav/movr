package us.blav.movr;

public interface IndexStorage {

  IndexWriter update ();

  interface IndexWriter extends AutoCloseable {

    void append (Entry entry);

    @Override
    void close ();
  }
}
