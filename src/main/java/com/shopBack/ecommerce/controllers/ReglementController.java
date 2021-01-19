package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.shopBack.ecommerce.domains.Reglement;
import com.shopBack.ecommerce.domains.Transaction;
import com.shopBack.ecommerce.repositories.TransactionRepository;
import com.shopBack.ecommerce.services.Impl.TransactionImpl;
import com.shopBack.ecommerce.services.ReglementService;
import com.shopBack.ecommerce.services.TransactionService;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/reglements")
public class ReglementController {

    private ReglementService reglementService;
    private TransactionService transactionService;
    private static final Logger log = LoggerFactory.getLogger(ReglementController.class);
    private Reglement reglement;
    private Transaction transaction;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    public ReglementController(ReglementService reglementService) {
        this.reglementService = reglementService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Reglement>> getAllReglements() {
        List<Reglement> reglements = reglementService.findAll();
        log.info("Reglement list size = {}", reglements.size());
        return ResponseEntity.ok(reglements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reglement> getReglementByID(@PathVariable int id) {
        try {
            Reglement reglement = reglementService.findById(id);
            return ResponseEntity.ok(reglement);

        }catch (Exception e){

            log.info("Reglement with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postReglement(@Valid @RequestBody Reglement reglement) {
        this.reglement = reglementService.save(reglement);
        if (this.reglement != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.reglement.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("Reglement is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReglement(@PathVariable int id, @RequestBody Reglement reglement) {
        try {
            reglementService.update(reglement, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReglement(@PathVariable int id) {
        try {
        reglementService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/transaction/create")
    public JSONObject callApiTransaction (@RequestBody Transaction transaction) throws UnirestException {
        System.out.println("*********************** Entrer fonction CREATE Transaction *******************");
        //Call API Transaction Paygates
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response  = Unirest.post("https://api.paygate.africa/transactions")
                .header("app_id", "$2a$10$wpM4MSm.Eu1gH.04Wz0YtO")
                .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfaWQiOiIzODFiODNhNmUxY2FlMmE4MzBjMDg3ZjY4NmI5MDM5NiIsImlhdCI6MTYxMDc0NTY4MSwiZXhwIjoxNjEwNzg4ODgxfQ.c7xErHWsQOf47hVDjFf6O6JBxUOBdl0n60han46YZavabJaYxbyRPwgHt1oOp72e9zzuHuIBcarcHohj-RTLQFzTI6HVxhhlzCebOYxF3Eg2zI1RQ3oqtMoP6NmrOo85moW2XtLfpZk5VR-fQmaeLNJFag3OiXsP2VYVjji--fdpF67MHrUjJdK-5g2Bt8m_IG3FthSND2Ibvsp8AN6UEh1lK2L8V-vObOquGebaa9FRNI9AAn-EkqAs9TmT6tkvgTxF7tCEV0mHRkIttfOo3Xioz9V9BvsWnyyrwrQbclFcK7r-30SM6yIe5S9xmXMfmdPbvL_4vYobOmsCEeWhEI4tPQJNI2aXSiCZ56xy1l0bFrPOsYrSxbit0xgQf2_3ZvuPCMXc3nOR-Zk8cyr4196cdQe3cLu9qcFpDsp8PcQoleEpHH6gVd44LOp26CfKiFFIwbFPADEmjrZuC0M52ahKjrlKEjEdMPAzCOHwVq39AB_ldGPnkm0UHmRX6u5BvkDg5xr7bXeD84xracrkd-ebvp4ySPi0_XM4b26nmFufrjl33wASASuSYaT9MFtWebT-vg9UyheLlCUimmWoKFyMXaigwM04gQ_qPtqWz_wJ0QMd3LaZEQnTFskdC20Wg8Zhni1MOynSAnkje9iCNT3uFomWiOUhick6kV0u_m4")
                .header("Content-Type", "application/json")
                .header("refresh_token", "$2a$10$afjTJVYNmIgVg.U1OTdIaOyUhLEMnbrHxm3DpQcUAblaJizU.SViC")
                .body("{\n    \"amount\": \""+transaction.getPrice().toString()+"\",\n    \"currency\": \""+transaction.getCurrency()+"\",\n    \"payment_options\": \""+transaction.getPayment_options()+"\",\n    \"order_ref\": \""+transaction.getOrder_ref()+"\",\n    \"items\": "+transaction.getItems()+",\n    \"cart\": [\n        {\n            \"product_name\": \""+transaction.getProduct_name()+"\",\n            \"product_code\": \""+transaction.getProduct_code()+"\",\n            \"quantity\": "+transaction.getQuantity()+",\n            \"price\": \""+transaction.getPrice()+"\",\n            \"total\": \""+transaction.getTotal()+"\",\n            \"image\": \"\",\n            \"description\": \"\",\n            \"note_1\": \"\",\n            \"note_2\": \"\",\n            \"note_3\": \"\",\n            \"note_4\": \"\",\n            \"note_5\": \"\"\n        }]\n    }")
                .asString();
        JSONObject auth = new JSONObject(response.getBody());
        System.out.println("aut" +auth);

        return  auth;
        //return ResponseEntity.ok(jsonResponse.getBody().getObject());
    }

    @PostMapping(value = "/transaction/create/bis",produces = "application/json")
    public String callAPI(@RequestBody Transaction transaction) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{\n    \"amount\": \""+transaction.getPrice()+"\",\n    \"currency\": \""+transaction.getCurrency()+"\",\n    \"payment_options\": \""+transaction.getPayment_options()+"\",\n    \"order_ref\": \""+transaction.getOrder_ref()+"\",\n    \"items\": "+transaction.getItems()+",\n    \"cart\": [\n        {\n            \"product_name\": \""+transaction.getProduct_name()+"\",\n            \"product_code\": \""+transaction.getProduct_code()+"\",\n            \"quantity\": "+transaction.getQuantity()+",\n            \"price\": \""+transaction.getPrice()+"\",\n            \"total\": \""+transaction.getTotal()+"\",\n            \"image\": \"\",\n            \"description\": \"\",\n            \"note_1\": \"\",\n            \"note_2\": \"\",\n            \"note_3\": \"\",\n            \"note_4\": \"\",\n            \"note_5\": \"\"\n        }]\n    }");
        Request request = new Request.Builder()
                .url("https://api.paygate.africa/transactions")
                .method("POST", body)
                .addHeader("app_id", "$2a$10$wpM4MSm.Eu1gH.04Wz0YtO") //dont change
                .addHeader("refresh_token", "$2a$10$afjTJVYNmIgVg.U1OTdIaOyUhLEMnbrHxm3DpQcUAblaJizU.SViC") //dont change
                .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfaWQiOiIzODFiODNhNmUxY2FlMmE4MzBjMDg3ZjY4NmI5MDM5NiIsImlhdCI6MTYxMTA4OTc4OSwiZXhwIjoxNjExMTMyOTg5fQ.Ucjg5s_hGeFD-e4f05dZn56IhYPUWQJO8QXvLdphzJnbz8s1IPkG8rM_s3SxxK76Css63D9bX8K4RPygiiyMJqRHkG01KF3t63uWIgbwYDSOtl8N3xMA9CVtqFz_hSc4Kf2r6IzvvEVq4xlF0x9QwCjvJ2SpQqX0brqH6y250PuLepwBWRVVizJ8z3g64SOfTTN8SKGqDrIdtXWvj_KK4t5jwf-2MjQJgDcBmwNgGoul1oeIpkAim7uXUBNwuyddBN6IiKA3oM-o-71Pcv1p1Qeq3rT3YHwXfGdluOV5EwgLpJtKdp6mfdCvmxQjkMN9ZPDyhACW6jgX6jBsT62EwmqiguoqKhAbo_vLGLX1pfvUdZNxwLSm--8uVabK5flFwsqT3rEeAeZub-cZZrjQ0dkzh4aMl8b5x2urLlLa3BU7bcBEKvImmTbCoDkBtIf2_GPYDlseY1W4Ec3ZDa0mVenUA1lCUosho7NVxGAmogXpsyBxcU9Ucb3sxfJVXyrSd1KpMT3nP5rhVchKpkpaTzmtbHghuw_CzqmWBAH2RECTVHs06wdgKrVLRmAZOshT3Tzro5nYdBLLdVBN4fOLQoSnfYFBuZEzZGk3r2GNeOqy3vUSbpDAp5JKi9MT-MBa6xlbtCCRn8T1uCTHhdPKRCBGpKBVz2YWGCc0GO9RLEE")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "pga_session_id=s%3Aw05PKI6cBTmy-Fqm1qPJHj32hwVlXggg.clzIIgVNHDkRLzQbTCxDWYx1RsDS2r7XdoxbTHDhvU4")
                .build();
        try (Response response = client.newCall(request).execute()){
            System.out.println("************** check Status reponse ************* ");
            if (!response.isSuccessful()){
                System.out.println("************** Status reponse not Success ************* ");
                System.out.println("************** Response body "+response.body().byteStream());
                return response.body().string() ;
                //throw new IOException("************* Unexpected code Not Success " + response.body().string());
            }

            // Get response body
           //System.out.println(response.body().string());
           String jsonData = response.body().string();
            try{
                JSONObject jsonObject = new JSONObject(jsonData);
                System.out.println("jsonObject"+jsonObject.getString("transaction_id"));
                String transactionId = jsonObject.getString("transaction_id");
                String applicationId = jsonObject.getString("application_id");
                String captureUrl = jsonObject.getString("capture_url");
                /**
                 * ********** Next Step Insert Into Database info above
                 */
                System.out.println("********************Before Save in Transaction *****************");
                Transaction transaction1 = new Transaction();
                transaction1.setPrice(transaction.getPrice());
                transaction1.setTransaction_id(transactionId);
                transaction1.setUrl(captureUrl);
                transaction1.setApplication_id(applicationId);
                transaction1.setCurrency(transaction.getCurrency());
                transaction1.setItems(transaction.getItems());
                transaction1.setOrder_ref(transaction.getOrder_ref());
                transaction1.setPrice(transaction.getPrice());
                transaction1.setQuantity(transaction.getQuantity());
                transaction1.setTotal(transaction.getTotal());
                transaction1.setStatus("PENDING");
                System.out.println("transaction 1 : "+transaction1);
                transactionRepository.save(transaction1);
                System.out.println("******************** After save in Transaction *****************");

            }catch (JSONException e){
                System.out.println("******************* Error JSON ***************");
                System.out.println(e.getMessage());
            }
            return jsonData ;
        }catch (IOException e){
            System.out.println("************** Error Message ************* ");
            System.out.println(e.getMessage());
            return "Not ok";
        }
    }

    @GetMapping("/payments_success")
    public void paymentSuccess(@RequestParam String transaction_id) {
        System.out.println("******************* Success Given Transaction_id : ["+transaction_id+"]");
    }

    @GetMapping("/payments_failure")
    public void paymentFailure(@RequestParam String transaction_id) {
        System.out.println("******************* Failure Given Transaction_id : ["+transaction_id+"]");
    }

    @GetMapping("/transaction/check_update")
    public String check_update(@RequestParam String transaction_id,@RequestParam String state) throws IOException {
        System.out.println("******************* Success Given Transaction_id : ["+transaction_id+"]");
        System.out.println("******************* Success Given state : ["+state+"]");
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.paygate.africa/transactions/"+transaction_id+"/payment")
                    .method("GET", null)
                    .addHeader("app_id", "$2a$10$wpM4MSm.Eu1gH.04Wz0YtO") //dont change
                    .addHeader("refresh_token", "$2a$10$afjTJVYNmIgVg.U1OTdIaOyUhLEMnbrHxm3DpQcUAblaJizU.SViC") //dont change
                    .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfaWQiOiIzODFiODNhNmUxY2FlMmE4MzBjMDg3ZjY4NmI5MDM5NiIsImlhdCI6MTYxMDc0NTY4MSwiZXhwIjoxNjEwNzg4ODgxfQ.c7xErHWsQOf47hVDjFf6O6JBxUOBdl0n60han46YZavabJaYxbyRPwgHt1oOp72e9zzuHuIBcarcHohj-RTLQFzTI6HVxhhlzCebOYxF3Eg2zI1RQ3oqtMoP6NmrOo85moW2XtLfpZk5VR-fQmaeLNJFag3OiXsP2VYVjji--fdpF67MHrUjJdK-5g2Bt8m_IG3FthSND2Ibvsp8AN6UEh1lK2L8V-vObOquGebaa9FRNI9AAn-EkqAs9TmT6tkvgTxF7tCEV0mHRkIttfOo3Xioz9V9BvsWnyyrwrQbclFcK7r-30SM6yIe5S9xmXMfmdPbvL_4vYobOmsCEeWhEI4tPQJNI2aXSiCZ56xy1l0bFrPOsYrSxbit0xgQf2_3ZvuPCMXc3nOR-Zk8cyr4196cdQe3cLu9qcFpDsp8PcQoleEpHH6gVd44LOp26CfKiFFIwbFPADEmjrZuC0M52ahKjrlKEjEdMPAzCOHwVq39AB_ldGPnkm0UHmRX6u5BvkDg5xr7bXeD84xracrkd-ebvp4ySPi0_XM4b26nmFufrjl33wASASuSYaT9MFtWebT-vg9UyheLlCUimmWoKFyMXaigwM04gQ_qPtqWz_wJ0QMd3LaZEQnTFskdC20Wg8Zhni1MOynSAnkje9iCNT3uFomWiOUhick6kV0u_m4")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", "pga_session_id=s%3Aw05PKI6cBTmy-Fqm1qPJHj32hwVlXggg.clzIIgVNHDkRLzQbTCxDWYx1RsDS2r7XdoxbTHDhvU4")
                    .build();

            try (Response response = client.newCall(request).execute()){
                System.out.println("************** check Status reponse ************* ");
                if (!response.isSuccessful()){
                    System.out.println("************** Status reponse not Success ************* ");
                    System.out.println("************** Response body "+response.body().byteStream());
                    return response.body().string() ;
                    //throw new IOException("************* Unexpected code Not Success " + response.body().string());
                }

            String jsonData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    System.out.println("jsonObject status payment get" + jsonObject.getJSONObject("payment").getString("status"));
                    String status = jsonObject.getJSONObject("payment").getString("status");
                    String currency = jsonObject.getString("currency");
                    String created_at = jsonObject.getString("createdAt");
                    String channel = jsonObject.getJSONObject("payment").getString("channel");
                    if (status.compareTo(state) == 0){ //IF SUCCESS IN API CHECK
                        System.out.println("******************* BOTH ARE SUCCESS ************************");
                        transactionRepository.setTransactionStateByTransactionId(transaction_id, currency, channel, state);
                        System.out.println("******************* After Update ************************");
                        return "PAYMENT ["+ status +"]";
                    }else { //ELSE (NOT SUCCESS IN CHEK || INCOHERENT)
                        System.out.println("******************* INCOHERENCE STATE ************************");
                        state = "INCOHERENT";
                        transactionRepository.setTransactionStateByTransactionId(transaction_id, currency, channel, state);
                        System.out.println("******************* After Update ************************");
                        return "PAYMENT SUCCESS INCOHERENT";
                    }
                }catch (JSONException e){ //JSON PARSE ERROR
                    System.out.println("******************* Error JSON ***************");
                    System.out.println(e.getMessage());
                    return "NOT OK JSON ERROR";
                }
            }catch (IOException e){ //OKHTTP REQUEST ERROR
                System.out.println("************** Error Message ************* ");
                System.out.println(e.getMessage());
                return "Not ok";
            }
    }
}
