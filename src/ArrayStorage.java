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
        boolean duplicate = false;
        for (Resume resume : getAll()) {
            if (resume.toString().equals(r.toString())) {
                System.out.println("Such uuid already exists");
                duplicate = true;
            }
        }
        if (!duplicate) {
            storage[size] = r;
            size++;
        }
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
        boolean notFound = true;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                storage[i] = null;
                for (int j = i; j < storage.length - 1; j++) {
                    storage[j] = storage[j + 1];
                }
                notFound = false;
                size--;
                break;
            }
        }
        if (notFound)
            System.out.println("No such uuid");
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
        return size;
    }
}
