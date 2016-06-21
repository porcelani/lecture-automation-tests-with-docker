import com.shekhargulati.reactivex.docker.client.DockerClient;
import com.shekhargulati.reactivex.docker.client.http_client.HttpStatus;
import com.shekhargulati.reactivex.docker.client.representations.DockerInfo;
import com.shekhargulati.reactivex.docker.client.representations.DockerVersion;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatusDockerHarness {

    public static final String DOCKER_HOST = "tcp://192.168.99.100:2376";
    public static final String DOCKER_CERT_PATH = "C:\\Users\\porce\\.docker\\machine\\certs";

    @Test
    public void statusDockerTest(){
        DockerClient client = DockerClient.newDockerClient(DOCKER_HOST, DOCKER_CERT_PATH);

        DockerVersion dockerVersion = client.serverVersion();
        assertEquals("1.9.0",dockerVersion.version());

        DockerInfo info = client.info();
        assertEquals(2,info.containers());
        assertEquals(129,info.images());

        HttpStatus ping = client.ping();
        assertEquals(200,ping.code());
        assertEquals("OK",ping.message());
    }
}
