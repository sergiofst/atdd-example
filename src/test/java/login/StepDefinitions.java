package login;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pe.atdd.example.domain.User;
import pe.atdd.example.exception.InvalidUserAndPasswordException;
import pe.atdd.example.repository.UserRepository;
import pe.atdd.example.service.LoginService;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StepDefinitions {

    String userName;
    String password;

    User actualUser;

    RuntimeException currentException;

    UserRepository userRepository = mock(UserRepository.class);
    LoginService loginService = new LoginService(userRepository);

    @Given("^Con usuario valido$")
    public void con_usuario_valido() throws Exception {
        userName = "usuario";
    }

    @Given("^contraseña valida$")
    public void contraseña_valida() throws Exception {
        password = "password";
    }

    @Given("^contraseña no valida$")
    public void contraseña_no_valida() throws Exception {
        password = "mal_password";
    }

    @When("^validar datos de inicio de sesion$")
    public void validar_datos_de_inicio_de_sesion() throws Exception {
        when(userRepository.findUser(anyString()))
                .thenReturn(
                        new User("usuario", "password", false)
                );

        try {
            actualUser = loginService.login(userName, password);
        } catch (RuntimeException t) {
            currentException = t;
        }
    }

    @Then("^inicio de sesion exitoso$")
    public void inicio_de_sesion_exitoso() throws Exception {
        assertNotNull(actualUser);
    }

    @Given("^es usuario no valido$")
    public void es_usuario_no_valido() throws Exception {
        userName = "usuario_no_valido";
    }

    @Then("^inicio de sesion no exitoso$")
    public void inicio_de_sesion_no_exitoso() throws Exception {
        assertTrue(currentException instanceof InvalidUserAndPasswordException);
    }


}
