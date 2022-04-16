package io.izitrak.contoller;

import io.izitrak.domain.dto.ClientDto;
import io.izitrak.domain.model.Client;
import io.izitrak.exception.ClientException;
import io.izitrak.payload.ApiResponse;
import io.izitrak.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;


    @PostMapping("/add-client/{userId}")
    public ResponseEntity<?> addAClient(@RequestBody ClientDto clientDto, @PathVariable Long userId){
        try {
            Client client = clientService.addClient(userId, clientDto);
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }catch (ClientException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateClientInfo(@RequestBody ClientDto clientDto){
            clientService.updateClient(clientDto);
            return new ResponseEntity<>(new ApiResponse(true, "Client info updated successfully"), HttpStatus.OK);
    }

    @GetMapping("/expired-clients")
    public ResponseEntity<?> getExpiredClient(){
        return new ResponseEntity<>(clientService.getAllExpiredClient(), HttpStatus.FOUND);
    }

    @GetMapping("/active-client")
    public ResponseEntity<?> getActiveClient(){
        return new ResponseEntity<>(clientService.getAllActiveClients(), HttpStatus.FOUND);
    }

    @GetMapping("/all-client")
    public ResponseEntity<?> getAllClient(){
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.FOUND);
    }

    @GetMapping("/about-expire")
    public ResponseEntity<?> getAboutToExpireClient(){
        return new ResponseEntity<>(clientService.getAllAboutToExpireClient(), HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable Long clientId){
        try {
            clientService.deleteClient(clientId);
            return new ResponseEntity<>(new ApiResponse(true, "Client deleted successfully"), HttpStatus.NO_CONTENT);
        }catch (ClientException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
