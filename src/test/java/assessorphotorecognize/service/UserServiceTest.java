package assessorphotorecognize.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.itmo.iad.photorecognize.domain.dao.UserDao;
import ru.itmo.iad.photorecognize.domain.repository.UserRepository;
import ru.itmo.iad.photorecognize.service.UserService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGettingUser() {
        Mockito.when(repository.findByTelegramId("1")).thenReturn(Optional.of(UserDao.builder().build()));

        User user = new User();
        user.setId(1L);
        user.setUserName("name");
        userService.getOrCreateUser(user);

        verify(repository).findByTelegramId("1");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testCreatingUser() {
        Mockito.when(repository.findByTelegramId("1")).thenReturn(Optional.empty());
        Mockito.when(repository.save(any())).thenReturn(null);

        User user = new User();
        user.setId(1L);
        user.setUserName("name");
        userService.getOrCreateUser(user);

        verify(repository).findByTelegramId("1");
        verify(repository).save(any());
        verifyNoMoreInteractions(repository);

    }

}
