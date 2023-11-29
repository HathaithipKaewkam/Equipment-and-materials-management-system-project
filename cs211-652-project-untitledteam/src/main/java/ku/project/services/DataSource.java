package ku.project.services;

public interface DataSource<T> {
    T readData();
    void writeData(T t);
}
