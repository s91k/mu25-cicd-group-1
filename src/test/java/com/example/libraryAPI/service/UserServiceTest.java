package com.example.libraryAPI.service;

import com.example.libraryAPI.dto.UserRequest;
import com.example.libraryAPI.exception.ResourceNotFoundException;
import com.example.libraryAPI.model.User;
import com.example.libraryAPI.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    private static UserRequest request(String firstName, String lastName) {
        return new UserRequest(firstName, lastName);
    }

    private static User createUserWithId(Long id, String firstName, String lastName) {
        User user = new User(firstName, lastName);
        user.setId(id);
        return user;
    }

    @Test
    void create_withValidRequest_savesAndReturnsUser() {
        UserRequest request = request("Karl", "Karlsson");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User toSave = invocation.getArgument(0);
                    toSave.setId(1L);
                    return toSave;
                });

        User result = userService.create(request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("Karl");
        assertThat(result.getLastName()).isEqualTo("Karlsson");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getFirstName()).isEqualTo("Karl");
    }

    @Test
    void create_withBlankFirstName_throwsIllegalArgumentException() {
        UserRequest request = request("", "Karlsson");

        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("firstName");

        verify(userRepository, never()).save(any());
    }

    @Test
    void create_withNullFirstName_throwsIllegalArgumentException() {
        UserRequest request = request(null, "Karlsson");

        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("firstName");

        verify(userRepository, never()).save(any());
    }

    @Test
    void create_withNullLastName_throwsIllegalArgumentException() {
        UserRequest request = request("Karl", null);

        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("lastName");

        verify(userRepository, never()).save(any());
    }

    @Test
    void create_withBlankLastName_throwsIllegalArgumentException() {
        UserRequest request = request("Karl", "  ");

        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("lastName");

        verify(userRepository, never()).save(any());
    }

    @Test
    void findAll_returnsAllUsers() {
        List<User> users = List.of(
                createUserWithId(1L, "Erik", "Eriksson"),
                createUserWithId(2L, "Anna", "Andersson"));
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertThat(result).hasSize(2).containsExactlyElementsOf(users);
    }

    @Test
    void findAll_whenEmpty_returnsEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = userService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void findById_whenExists_returnsUser() {
        User existing = createUserWithId(1L, "Erik", "Eriksson");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));

        User result = userService.findById(1L);

        assertThat(result).isEqualTo(existing);
    }

    @Test
    void findById_whenMissing_throwsResourceNotFoundException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");
    }


    @Test
    void update_withValidRequest_updatesAndReturnsUser() {
        User existing = createUserWithId(1L, "Sven", "Svensson");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.update(1L, request("Sven", "Andersson"));

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("Sven");
        assertThat(result.getLastName()).isEqualTo("Andersson");
        verify(userRepository).save(existing);
    }

    @Test
    void update_whenMissing_throwsResourceNotFoundException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(999L, request("Ghost", "User")))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void update_withBlankFirstName_throwsIllegalArgumentExceptionWithoutTouchingRepository() {
        UserRequest request = request("", "Andersson");

        assertThatThrownBy(() -> userService.update(1L, request))
                .isInstanceOf(IllegalArgumentException.class);

        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
    }


    @Test
    void delete_whenExists_removesUser() {
        User existing = createUserWithId(1L, "Anna", "Andersson");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));

        userService.delete(1L);

        verify(userRepository, times(1)).delete(existing);
    }

    @Test
    void delete_whenMissing_throwsResourceNotFoundException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.delete(999L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(userRepository, never()).delete(any());
    }
}