/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
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
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];

        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
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
