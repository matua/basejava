/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume toFind = null;
        for (int i = 0; i < size(); i++) {
            if (storage[i].toString().equals(uuid)) {
                toFind = storage[i];
                break;
            }
        }
        return toFind;
    }

    void delete(String uuid) {
        int count = 0;
        for (Resume resume : storage) {
            if (resume.toString().equals(uuid)) {
                resume = null;
                break;
            }
            count++;
        }

        if (storage.length - 1 - count >= 0)
            System.arraycopy(storage, count + 1, storage, count, storage.length - 1 - count);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] noNullStorage = new Resume[size()];

        if (size() >= 0) System.arraycopy(storage, 0, noNullStorage, 0, size());
        return noNullStorage;
    }

    int size() {
        int count = 0;
        for (Resume resume : storage) {
            if (resume == null) {
                break;
            }
            count++;
        }
        return count;
    }
}
