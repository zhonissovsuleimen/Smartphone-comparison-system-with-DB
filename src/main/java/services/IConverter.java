package services;

import java.io.FileNotFoundException;
import java.util.List;

public interface IConverter<T> {
    T Convert(String txtDirectory) throws FileNotFoundException;
    List<T> ConvertAll(String folderDirectory);
}
