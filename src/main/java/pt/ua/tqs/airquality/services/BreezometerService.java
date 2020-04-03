package pt.ua.tqs.airquality.services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.springframework.stereotype.Service;

@Service
public class BreezometerService {
    private final Client client = ClientBuilder.newClient();

}
