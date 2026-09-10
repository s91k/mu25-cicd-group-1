package com.example.libraryAPI.repository;

import com.example.libraryAPI.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    private static User user(String firstName, String lastName) {
        return new User(firstName, lastName);
    }

    @Test
    void save_whenIdIsNull_assignsGeneratedId() {
        User saved = userRepository.save(user("Karl", "Karlsson"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getId()).isEqualTo(1L);
    }

    @Test
    void save_whenCalledMultipleTimes_assignsIncrementingIds() {
        User first = userRepository.save(user("Karl", "Karlsson"));
        User second = userRepository.save(user("Erik", "Eriksson"));

        assertThat(first.getId()).isEqualTo(1L);
        assertThat(second.getId()).isEqualTo(2L);
    }

    @Test
    void save_whenIdAlreadySet_keepsExistingIdAndOverwritesStoredUser() {
        User saved = userRepository.save(user("Karl", "Karlsson"));
        Long id = saved.getId();

        saved.setLastName("Andersson");
        User resaved = userRepository.save(saved);

        assertThat(resaved.getId()).isEqualTo(id);
        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(userRepository.findById(id).get().getLastName()).isEqualTo("Andersson");
    }

       @Test
    void findAll_returnsAllSavedUsers() {
        userRepository.save(user("Karl", "Karlsson"));
        userRepository.save(user("Erik", "Eriksson"));

        List<User> all = userRepository.findAll();

        assertThat(all).hasSize(2);
        assertThat(all).extracting(User::getFirstName).containsExactly("Karl", "Erik");
    }

    @Test
    void findAll_whenEmpty_returnsEmptyList() {
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    void findAll_returnsCopy_doesNotExposeInternalStorage() {
        userRepository.save(user("Karl", "Karlsson"));

        List<User> all = userRepository.findAll();
        all.clear();

        assertThat(userRepository.findAll()).hasSize(1);
    }


    @Test
    void findById_whenExists_returnsUser() {
        User saved = userRepository.save(user("Karl", "Karlsson"));

        Optional<User> found = userRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Karl");
    }

    @Test
    void findById_whenMissing_returnsEmptyOptional() {
        Optional<User> found = userRepository.findById(999L);

        assertThat(found).isEmpty();
    }


    @Test
    void existsById_whenExists_returnsTrue() {
        User saved = userRepository.save(user("Karl", "Karlsson"));

        assertThat(userRepository.existsById(saved.getId())).isTrue();
    }

    @Test
    void existsById_whenMissing_returnsFalse() {
        assertThat(userRepository.existsById(999L)).isFalse();
    }


    @Test
    void delete_removesUser() {
        User saved = userRepository.save(user("Karl", "Karlsson"));

        userRepository.delete(saved);

        assertThat(userRepository.findById(saved.getId())).isEmpty();
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    void delete_whenUserNotStored_doesNotThrowOrAffectOthers() {
        User saved = userRepository.save(user("Karl", "Karlsson"));
        User notStored = user("Ghost", "User");
        notStored.setId(999L);

        userRepository.delete(notStored);

        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(userRepository.findById(saved.getId())).isPresent();
    }
}
