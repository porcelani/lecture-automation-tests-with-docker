import com.shekhargulati.reactivex.docker.client.DockerClient;
import com.shekhargulati.reactivex.docker.client.http_client.HttpStatus;
import com.shekhargulati.reactivex.docker.client.representations.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class StartContainerWithExportPortsHarness {

    public static final String DOCKER_HOST = "tcp://192.168.99.100:2376";
    public static final String DOCKER_CERT_PATH = "C:\\Users\\porce\\.docker\\machine\\certs";

    @Test
    public void bla(){
        DockerClient client = DockerClient.newDockerClient(DOCKER_HOST, DOCKER_CERT_PATH);

        final String[] exposedPorts = new String[]{"80/tcp"};
        final String[] hostPorts = new String[]{"80/tcp"};

        //star
        final Map<String, List<PortBinding>> portBindings = new HashMap<>();
        for (String hostPort : hostPorts) {
            List<PortBinding> hostPortBinding = new ArrayList<>();
            hostPortBinding.add(PortBinding.of("0.0.0.0", hostPort));
            portBindings.put(hostPort, hostPortBinding);
        }
        final HostConfig hostConfig = new HostConfigBuilder().setPortBindings(portBindings).createHostConfig();
        DockerContainerRequest request = new DockerContainerRequestBuilder()
                .setImage("citizenstig/dvwa-modificado")
                .setCmd(Arrays.asList("/run.sh"))
                .setAttachStdin(true)
                .addExposedPort(exposedPorts)
                .setHostConfig(hostConfig)
                .setTty(true)
                .createDockerContainerRequest();
        String container = "my_container";
        DockerContainerResponse response = client.createContainer(request, container);
        client.startContainer(response.getId());

        //stop
        client.stopContainer(container,5 );
        HttpStatus status = client.waitContainer(container);
        assertEquals(200,status.code());

        //remove
        HttpStatus httpStatusRemove = client.removeContainer(container);
        assertEquals(204,httpStatusRemove.code());


    }
}
