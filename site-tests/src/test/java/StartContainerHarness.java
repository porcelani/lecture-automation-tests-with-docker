import com.shekhargulati.reactivex.docker.client.DockerClient;
import com.shekhargulati.reactivex.docker.client.http_client.HttpStatus;
import com.shekhargulati.reactivex.docker.client.representations.DockerContainerRequest;
import com.shekhargulati.reactivex.docker.client.representations.DockerContainerRequestBuilder;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StartContainerHarness {

    public static final String DOCKER_HOST = "tcp://192.168.99.100:2376";
    public static final String DOCKER_CERT_PATH = "C:\\Users\\porce\\.docker\\machine\\certs";

    @Test
    public void bla() {
        DockerClient client = DockerClient.newDockerClient(DOCKER_HOST, DOCKER_CERT_PATH);

        DockerContainerRequest request = new DockerContainerRequestBuilder()
                .setImage("ubuntu")
                .setCmd(Arrays.asList("/bin/bash"))
                .setAttachStdin(true)
                .setTty(true)
                .createDockerContainerRequest();

        String container = "my_first_container";
        client.createContainerObs(request, container)
                .flatMap(res -> client.startContainerObs(res.getId()))
                .subscribe(System.out::println);

        client.stopContainer(container, 5);
        HttpStatus status = client.waitContainer(container);
        assertEquals(200, status.code());
    }
}
