package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.shopBack.ecommerce.domains.*;
import com.shopBack.ecommerce.repositories.CommandeRepository;
import com.shopBack.ecommerce.repositories.LigneCommandeRepository;
import com.shopBack.ecommerce.repositories.TransactionRepository;
import com.shopBack.ecommerce.services.Impl.TransactionImpl;
import com.shopBack.ecommerce.services.LigneCommandeService;
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
    private LigneCommandeRepository ligneCommandeRepository;
    @Autowired
    private CommandeRepository commandeRepository;

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

        } catch (Exception e) {

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
    public JSONObject callApiTransaction(@RequestBody Transaction transaction) throws UnirestException {
        System.out.println("*********************** Entrer fonction CREATE Transaction *******************");
        //Call API Transaction Paygates
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("https://api.paygate.africa/transactions")
                .header("app_id", "$2a$10$wpM4MSm.Eu1gH.04Wz0YtO")
                .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfaWQiOiIzODFiODNhNmUxY2FlMmE4MzBjMDg3ZjY4NmI5MDM5NiIsImlhdCI6MTYxMDc0NTY4MSwiZXhwIjoxNjEwNzg4ODgxfQ.c7xErHWsQOf47hVDjFf6O6JBxUOBdl0n60han46YZavabJaYxbyRPwgHt1oOp72e9zzuHuIBcarcHohj-RTLQFzTI6HVxhhlzCebOYxF3Eg2zI1RQ3oqtMoP6NmrOo85moW2XtLfpZk5VR-fQmaeLNJFag3OiXsP2VYVjji--fdpF67MHrUjJdK-5g2Bt8m_IG3FthSND2Ibvsp8AN6UEh1lK2L8V-vObOquGebaa9FRNI9AAn-EkqAs9TmT6tkvgTxF7tCEV0mHRkIttfOo3Xioz9V9BvsWnyyrwrQbclFcK7r-30SM6yIe5S9xmXMfmdPbvL_4vYobOmsCEeWhEI4tPQJNI2aXSiCZ56xy1l0bFrPOsYrSxbit0xgQf2_3ZvuPCMXc3nOR-Zk8cyr4196cdQe3cLu9qcFpDsp8PcQoleEpHH6gVd44LOp26CfKiFFIwbFPADEmjrZuC0M52ahKjrlKEjEdMPAzCOHwVq39AB_ldGPnkm0UHmRX6u5BvkDg5xr7bXeD84xracrkd-ebvp4ySPi0_XM4b26nmFufrjl33wASASuSYaT9MFtWebT-vg9UyheLlCUimmWoKFyMXaigwM04gQ_qPtqWz_wJ0QMd3LaZEQnTFskdC20Wg8Zhni1MOynSAnkje9iCNT3uFomWiOUhick6kV0u_m4")
                .header("Content-Type", "application/json")
                .header("refresh_token", "$2a$10$afjTJVYNmIgVg.U1OTdIaOyUhLEMnbrHxm3DpQcUAblaJizU.SViC")
//                .body("{\n    \"amount\": \"" + transaction.getAmount().toString() + "\",\n    \"currency\": \"" + transaction.getCurrency() + "\",\n    \"payment_options\": \"" + transaction.getPayment_options() + "\",\n    \"order_ref\": \"" + transaction.getOrder_ref() + "\",\n    \"items\": " + transaction.getItems() + ",\n    \"cart\": [\n        {\n            \"product_name\": \"" + transaction.getProduct_name() + "\",\n            \"product_code\": \"" + transaction.getProduct_code() + "\",\n            \"quantity\": " + transaction.getQuantity() + ",\n            \"price\": \"" + transaction.getPrice() + "\",\n            \"total\": \"" + transaction.getTotal() + "\",\n            \"image\": \"\",\n            \"description\": \"\",\n            \"note_1\": \"\",\n            \"note_2\": \"\",\n            \"note_3\": \"\",\n            \"note_4\": \"\",\n            \"note_5\": \"\"\n        }]\n    }")
                .asString();
        JSONObject auth = new JSONObject(response.getBody());
        System.out.println("aut" + auth);

        return auth;
        //return ResponseEntity.ok(jsonResponse.getBody().getObject());
    }

//    @PostMapping(value = "/transaction/create/bis", produces = "application/json")
//    public String callAPI(@RequestBody Transaction transaction) throws IOException {
//        System.out.println("**********Request Body"+transaction);
//        String transactionInfo = payGateAddTransaction(transaction, null);
//       // System.out.println("********* transaction info "+transactionInfo.string());
//        try {
//            JSONObject jsonObject = new JSONObject(transactionInfo);
//            if (jsonObject.has("message")) {
//                System.out.println("******************* Getting messsage in JSON DUE TO TOKEN");
//                String received_message = jsonObject.getString("message");
//                //if true then log in, reconnect app and relaunch request with good bearer token
//                if (received_message.compareTo("Access restricted ") == 0) {
//                    System.out.println("******************* ACCESS RESTRICTED GETTING NEW TOKEN");
//                    //logIn
//                    String newToken = ConnectAndGetToken();
//                    if (newToken != null) {
//                        System.out.println("******************* SEND REQUEST WITH NEW TOKEN");
//                        //relaunch payment with new token
//                        transactionInfo = payGateAddTransaction(transaction, newToken);
//                        System.out.println("transaction Info "+transactionInfo);
//                        jsonObject = new JSONObject(transactionInfo);
//                        //System.out.println("Json Object : ");
//                        System.out.println(jsonObject.toString());
//                        System.out.println("jsonObject" + jsonObject);
//                        String transactionId = jsonObject.getString("transaction_id");
//                        String applicationId = jsonObject.getString("application_id");
//                        String captureUrl = jsonObject.getString("capture_url");
//                        /**
//                         * ********** Next Step Insert Into Database info above
//                         */
//                        System.out.println("********************Before Save in Transaction *****************");
//                        Transaction transaction1 = new Transaction();
////                        transaction1.setPrice(transaction.getPrice());
//                        transaction1.setTransaction_id(transactionId);
//                        transaction1.setUrl(captureUrl);
//                        transaction1.setApplication_id(applicationId);
//                        transaction1.setCurrency(transaction.getCurrency());
//                        transaction1.setItems(transaction.getItems());
//                        transaction1.setOrder_ref(transaction.getOrder_ref());
////                        transaction1.setQuantity(transaction.getQuantity());
////                        transaction1.setTotal(transaction.getTotal());
////                        transaction1.setIdCommande(transaction.getIdCommande());
//                        transaction1.setStatus("PENDING");
//                        System.out.println("transaction 1 : " + transaction1);
//                        transactionRepository.save(transaction1);
//                        System.out.println("******************** After save in Transaction *****************");
//                        return transactionInfo;
//
//                    } else {
//                        return "GET TOKEN ERROR";
//                    }
//                } else {
//                    return "UNKNOWN ERROR" + received_message;
//                }
//            }else {
//                System.out.println("jsonObject" + jsonObject.getString("transaction_id"));
//                String transactionId = jsonObject.getString("transaction_id");
//                String applicationId = jsonObject.getString("application_id");
//                String captureUrl = jsonObject.getString("capture_url");
//                System.out.println("********************Before Save in Transaction *****************");
//                Transaction transaction1 = new Transaction();
////                transaction1.setPrice(transaction.getPrice());
//                transaction1.setTransaction_id(transactionId);
//                transaction1.setUrl(captureUrl);
//                transaction1.setApplication_id(applicationId);
//                transaction1.setCurrency(transaction.getCurrency());
//                transaction1.setItems(transaction.getItems());
//                transaction1.setOrder_ref(transaction.getOrder_ref());
////                transaction1.setIdCommande(transaction.getIdCommande());
////                transaction1.setQuantity(transaction.getQuantity());
////                transaction1.setTotal(transaction.getTotal());
//                transaction1.setStatus("PENDING");
//                System.out.println("transaction 1 : " + transaction1);
//                transactionRepository.save(transaction1);
//                System.out.println("******************** After save in Transaction *****************");
//                return transactionInfo;
//            }
//            }catch (JSONException e){ //JSON PARSE ERROR
//            System.out.println("******************* Error JSON ***************");
//            System.out.println(e.getMessage());
//            return "NOT OK JSON ERROR";
//            }
//    }

    @PostMapping(value = "/transaction/create/Json", produces = "application/json")
    public String createTransactionFronJson(@RequestBody TransactionWraper transactionWraper) throws IOException{
        System.out.println("~~~~~~~~~~~~~~~~~~~~IN FUNCTION~~~~~~~~~~~~~~~~~~~~~~~");
        Transaction transaction = new Transaction();
        transaction.setApplication_id(transactionWraper.getApplication_id());
        transaction.setCurrency(transactionWraper.getCurrency());
        transaction.setItems(transactionWraper.getItems());
        transaction.setOrder_ref(transactionWraper.getOrder_ref());
        transaction.setStatus("PENDING");
        transaction.setPayment_options(transactionWraper.getPayment_options());
        transaction.setDate(transactionWraper.getDate());
        transaction.setAmount(transactionWraper.getAmount());
        transaction.setStatus_commande("INIT");
        System.out.println("~~~~~~~~~~~~~~~~~~~~ BEFORE SAVING TRANSACTION ~~~~~~~~~~~~~~~~~~~~~~~");

        transaction = this.transactionRepository.save(transaction);

        if(transaction != null){
            System.out.println("~~~~~~~~~~~~~~~~~~~~TRANSACTION SAVED~~~~~~~~~~~~~~~~~~~~~~~");

            for(Cart cart: transactionWraper.getCart()){
                System.out.println("******* cart");
                System.out.println(cart.getProduct_name());
                LigneCommande ligne_commande = new LigneCommande();
                ligne_commande.setClientIdClient(0);
                ligne_commande.setPrixTelephone(cart.getPrice());
                ligne_commande.setQuantity(cart.getQuantity());
                ligne_commande.setTotal(cart.getTotal());
                ligne_commande.setProduct_code(cart.getProduct_code());
                ligne_commande.setProduct_name(cart.getProduct_name());
                ligne_commande.setIdTransaction(transaction.getId());
                if(transactionWraper.getSelected_payment_way().compareTo("Mobile Money") == 0){
                    ligne_commande.setIdmodeReglement(1);
                }else{
                    ligne_commande.setIdmodeReglement(2);
                }
                if(cart.getForfait() != null){
                    ligne_commande.setContainforfait(true);
                    ligne_commande.setEngagement(cart.getForfait().getTypePrePostPaid());
                    ligne_commande.setIdForfait(cart.getForfait().getId());
                    ligne_commande.setMontantForfait(cart.getForfait().getMontant().toString());
                    if(cart.getForfait().getTypePrePostPaid().compareTo("prePaid") == 0){
                        ligne_commande.setNbMoisEngagement(0);
                    }else{
                        ligne_commande.setNbMoisEngagement(12);
                    }
                }else{
                    ligne_commande.setContainforfait(false);
                    ligne_commande.setEngagement("");
                    ligne_commande.setIdForfait(0);
                    ligne_commande.setMontantForfait("0");
                    ligne_commande.setNbMoisEngagement(0);
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~BEFORE SAVING LIGNE COMMANDE~~~~~~~~~~~~~~~~~~~~~~~");

                ligne_commande = this.ligneCommandeRepository.save(ligne_commande);
                if(ligne_commande == null){
                    System.out.println("~~~~~~~~~~~~~~~~~~~~ERROR WHILE SAVING LIGNE COMMANDE~~~~~~~~~~~~~~~~~~~~~~~");

                    this.transactionRepository.deleteById(transaction.getId());
                    return "Error while adding order details";
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~LIGNE COMMANDE SAVED~~~~~~~~~~~~~~~~~~~~~~~");

            }

            if(transactionWraper.getSelected_payment_way().compareTo("Mobile Money") == 0){
                System.out.println("~~~~~~~~~~~~~~~~~~~~SENDING TO PAYGATE SAVED~~~~~~~~~~~~~~~~~~~~~~~");
                String transactionInfo = this.payGateAddTransaction(transactionWraper, null);
                try {
                    JSONObject jsonObject = new JSONObject(transactionInfo);
                    if (jsonObject.has("message")) {
                        System.out.println("******************* Getting messsage in JSON DUE TO TOKEN");
                        String received_message = jsonObject.getString("message");
                        //if true then log in, reconnect app and relaunch request with good bearer token
                        if (received_message.compareTo("Access restricted ") == 0) {
                            System.out.println("******************* ACCESS RESTRICTED GETTING NEW TOKEN");
                            //logIn
                            String newToken = ConnectAndGetToken();
                            if (newToken != null) {
                                System.out.println("******************* SEND REQUEST WITH NEW TOKEN");
                                //relaunch payment with new token
                                transactionInfo = payGateAddTransaction(transactionWraper, newToken);
                                System.out.println("transaction Info "+transactionInfo);
                                jsonObject = new JSONObject(transactionInfo);
                                //System.out.println("Json Object : ");
                                System.out.println(jsonObject.toString());
                                System.out.println("jsonObject" + jsonObject);
                                String transactionId = jsonObject.getString("transaction_id");
                                String applicationId = jsonObject.getString("application_id");
                                String captureUrl = jsonObject.getString("capture_url");
                                /**
                                 * ********** Next Step Insert Into Database info above
                                 */
                                transaction.setTransaction_id(transactionId);
                                transaction.setUrl(captureUrl);
                                this.transactionRepository.save(transaction);
                                return transactionInfo;

                            } else {
                                return "GET TOKEN ERROR";
                            }
                        } else {
                            return "UNKNOWN ERROR" + received_message;
                        }
                    }else {
                        System.out.println("jsonObject" + jsonObject.getString("transaction_id"));
                        String transactionId = jsonObject.getString("transaction_id");
                        String applicationId = jsonObject.getString("application_id");
                        String captureUrl = jsonObject.getString("capture_url");
                        transaction.setTransaction_id(transactionId);
                        transaction.setUrl(captureUrl);
                        this.transactionRepository.save(transaction);
                        return transactionInfo;

                    }
                }catch (JSONException e){ //JSON PARSE ERROR
                    System.out.println("******************* Error JSON ***************");
                    System.out.println(e.getMessage());
                    return "NOT OK JSON ERROR";
                }
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~NO NEED TO SEND TO PAYGATE~~~~~~~~~~~~~~~~~~~~~~~");


        }
        return "Error";
    }


    @GetMapping("/payments_success")
    public void paymentSuccess(@RequestParam String transaction_id) {
        System.out.println("******************* Success Given Transaction_id : [" + transaction_id + "]");
    }

    @GetMapping("/payments_failure")
    public void paymentFailure(@RequestParam String transaction_id) {
        System.out.println("******************* Failure Given Transaction_id : [" + transaction_id + "]");
    }

    /**
     * Update Transaction after payment
     * @param transaction_id
     * @param state
     * @return
     * @throws IOException
     */
    @GetMapping("/transaction/check_update")
    public String check_update(@RequestParam String transaction_id, @RequestParam String state) throws IOException {
        System.out.println("******************* Success Given Transaction_id : [" + transaction_id + "]");
        System.out.println("******************* Success Given state : [" + state + "]");
        return callPayGateApi(transaction_id, state);
    }

    /**
     *
     * @param transaction_id
     * @param state
     * @return
     * @throws IOException
     */
    public String callPayGateApi(String transaction_id, String state) throws IOException{

        String transactionInfo = payGateCheckTransaction(transaction_id, null);
        try {
            JSONObject jsonObject = new JSONObject(transactionInfo);
            if (jsonObject.has("message")) {
                System.out.println("******************* Getting messsage in JSON DUE TO TOKEN");
                String received_message = jsonObject.getString("message");
                //if true then log in, reconnect app and relaunch request with good bearer token
                if (received_message.compareTo("Access restricted ") == 0) {
                    System.out.println("******************* ACCESS RESTRICTED GETTING NEW TOKEN");
                    //logIn
                    String newToken = ConnectAndGetToken();
                    if (newToken != null) {
                        System.out.println("******************* SEND REQUEST WITH NEW TOKEN");
                        //relaunch payment with new token
                        transactionInfo = payGateCheckTransaction(transaction_id, newToken);
                        jsonObject = new JSONObject(transactionInfo);
                        //EVRYTHING IS OK
                        System.out.println("jsonObject status payment get" + jsonObject.getJSONObject("payment").getString("status"));
                        String status = jsonObject.getJSONObject("payment").getString("status");
                        String currency = jsonObject.getString("currency");
                        String created_at = jsonObject.getString("createdAt");
                        String channel = jsonObject.getJSONObject("payment").getString("channel");
                        if (status.compareTo(state) == 0){ //IF SUCCESS IN API CHECK
                            System.out.println("******************* BOTH ARE SUCCESS ************************");
                            this.UpdateTableAfterTransactionCallBack( transaction_id, currency, channel, state);
                            return "PAYMENT ["+ status +"]";
                        }else { //ELSE (NOT SUCCESS IN CHEK || INCOHERENT)
                            System.out.println("******************* INCOHERENCE STATE ************************");
                            state = "INCOHERENT";
                            this.UpdateTableAfterTransactionCallBack( transaction_id, currency, channel, state);
                            return "PAYMENT SUCCESS INCOHERENT";
                        }

                    } else {
                        return "GET TOKEN ERROR";
                    }
                } else {
                    return "UNKNOWN ERROR" + received_message;
                }
            } else {
                //EVRYTHING IS OK
                System.out.println("jsonObject status payment get" + jsonObject.getJSONObject("payment").getString("status"));
                String status = jsonObject.getJSONObject("payment").getString("status");
                String currency = jsonObject.getString("currency");
                String created_at = jsonObject.getString("createdAt");
                String channel = jsonObject.getJSONObject("payment").getString("channel");
                if (status.compareTo(state) == 0){ //IF SUCCESS IN API CHECK
                    System.out.println("******************* BOTH ARE SUCCESS ************************");
                    this.UpdateTableAfterTransactionCallBack( transaction_id, currency, channel, state);
                    return "PAYMENT ["+ status +"]";
                }else { //ELSE (NOT SUCCESS IN CHEK || INCOHERENT)
                    System.out.println("******************* INCOHERENCE STATE ************************");
                    state = "INCOHERENT";
                    this.UpdateTableAfterTransactionCallBack( transaction_id, currency, channel, state);
                    return "PAYMENT SUCCESS INCOHERENT";
                }
            }
        }catch (JSONException e){ //JSON PARSE ERROR
                System.out.println("******************* Error JSON ***************");
                System.out.println(e.getMessage());
                return "NOT OK JSON ERROR";
        }
    }

    public String ConnectAndGetToken() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.paygate.africa/auth/connect/application")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("app_id", "$2a$10$wpM4MSm.Eu1gH.04Wz0YtO")
                .addHeader("Authorization", "Basic MzgxYjgzYTZlMWNhZTJhODMwYzA4N2Y2ODZiOTAzOTY6JDJhJDEwJGFmalRKVllObUlnVmcuVTFPVGRJYU9JZkR5NGVSY2YuN09ILzJaZnRRd21UYjhOc1VWMHU2")
                .addHeader("Cookie", "pga_session_id=s%3AtppsX4yXfEfnXHYnwWHpZBIIjsDb8_5L.Rl1RAZEFW%2Fgbchp2pJ%2FoY6LTdAfR3S2fhMipyaje9pU")
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return jsonObject.getString("connect_token");
        }catch (JSONException e){ //JSON PARSE ERROR
            System.out.println("******************* Error JSON WHILE GETTING NEW TOKEN ***************");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Check transaction state in paygate
     * @param transaction_id
     * @param Token
     * @return
     * @throws IOException
     */
    public String payGateCheckTransaction(String transaction_id, String Token)  throws IOException{
        if(Token == null){
            Token = "33332";
//            Token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfaWQiOiIzODFiODNhNmUxY2FlMmE4MzBjMDg3ZjY4NmI5MDM5NiIsImlhdCI6MTYxMTE3MTk2MCwiZXhwIjoxNjExMjE1MTYwfQ.HSt3Jum6YizwYP7zBnke4PUQY9Y67aE_os4G2GonkISMDusUa6I55ASRw1Kk0NZTBfxVrtjEINpLCtI1Q6uiN3qD4qe-CgQ3xDhnLQ3m-HXRfPocIwsKhZ2J-dM0sebxnrrCAXgxD9q32yTqD-XlUO8-KMhc-3RUhRxkyt0Kc47PSNWu-h1GAAEOe2SIosq0Mq3fmsINrC_HLLmmrJ1fjZoYm_vPZPOCeIXpm11hyy2KBsxHP-KpveFgIzBzB6ie74up5JznT-7_goz7vHc33eWpGuchbXy0JYTbWWDC-2xJklz8O839GWnc62W2OTpjuRAcabOU0coTr5EOI69Tr9z4aOCWYlqtCX0RvMRym8ID54kBom89sVAOpbTpbtpoNpOHd77SfMFH-MAsZfEpbNp_NiXtpRjwlUOsGNHi7QTrHUni31rhTOAE_p51c7NrtUs6v5hQtBqA7bYlsw7n_FejXL1CsiU01GeDpuUgHLkZs2s1Gu8e44CIg3mPVG7roai-S7r0u1WiuU1xbdp7smncmikHJaKZ5VRYoTG-qpOUly4Fbt7kQzt-lpj65xFXJ60ef6qfYcZqp9Ux-8-oTrKHyuE5D3Lx9D7BJTrxBeVblMNg4ou_QJ3haykGudto2R-95gDuROb_M4xlp8-b3muDVW4VqSMtpwqqVJVNLtY";
        }
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.paygate.africa/transactions/"+transaction_id+"/payment")
                .method("GET", null)
                .addHeader("app_id", "$2a$10$wpM4MSm.Eu1gH.04Wz0YtO") //dont change
                .addHeader("refresh_token", "$2a$10$afjTJVYNmIgVg.U1OTdIaOyUhLEMnbrHxm3DpQcUAblaJizU.SViC") //dont change
                .addHeader("Authorization", Token)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "pga_session_id=s%3Aw05PKI6cBTmy-Fqm1qPJHj32hwVlXggg.clzIIgVNHDkRLzQbTCxDWYx1RsDS2r7XdoxbTHDhvU4")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()){
            System.out.println("************** Status reponse not Success ************* ");
            System.out.println("************** Response body "+response.body().byteStream());
            return response.body().string();
//            return "HTTP ERROR";
            //throw new IOException("************* Unexpected code Not Success " + response.body().string());
        }
        return response.body().string();
    }

    /**
     * Call API Paygate To Add Transaction
     * @param transaction
     * @param Token
     * @return
     * @throws IOException
     */
    public String payGateAddTransaction(TransactionWraper transaction, String Token)  throws IOException{

        if(Token == null){
            Token = "33332";
//            Token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfaWQiOiIzODFiODNhNmUxY2FlMmE4MzBjMDg3ZjY4NmI5MDM5NiIsImlhdCI6MTYxMTE3MTk2MCwiZXhwIjoxNjExMjE1MTYwfQ.HSt3Jum6YizwYP7zBnke4PUQY9Y67aE_os4G2GonkISMDusUa6I55ASRw1Kk0NZTBfxVrtjEINpLCtI1Q6uiN3qD4qe-CgQ3xDhnLQ3m-HXRfPocIwsKhZ2J-dM0sebxnrrCAXgxD9q32yTqD-XlUO8-KMhc-3RUhRxkyt0Kc47PSNWu-h1GAAEOe2SIosq0Mq3fmsINrC_HLLmmrJ1fjZoYm_vPZPOCeIXpm11hyy2KBsxHP-KpveFgIzBzB6ie74up5JznT-7_goz7vHc33eWpGuchbXy0JYTbWWDC-2xJklz8O839GWnc62W2OTpjuRAcabOU0coTr5EOI69Tr9z4aOCWYlqtCX0RvMRym8ID54kBom89sVAOpbTpbtpoNpOHd77SfMFH-MAsZfEpbNp_NiXtpRjwlUOsGNHi7QTrHUni31rhTOAE_p51c7NrtUs6v5hQtBqA7bYlsw7n_FejXL1CsiU01GeDpuUgHLkZs2s1Gu8e44CIg3mPVG7roai-S7r0u1WiuU1xbdp7smncmikHJaKZ5VRYoTG-qpOUly4Fbt7kQzt-lpj65xFXJ60ef6qfYcZqp9Ux-8-oTrKHyuE5D3Lx9D7BJTrxBeVblMNg4ou_QJ3haykGudto2R-95gDuROb_M4xlp8-b3muDVW4VqSMtpwqqVJVNLtY";
        }
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        System.out.println("TRANSACTIO JSON " + transaction.toString());
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, transaction.toString());
        System.out.println("new Token"+Token);
//        System.out.println("{\n    \"amount\": \"" + transaction.getAmount() + "\",\n    \"currency\": \"" + transaction.getCurrency() + "\",\n    \"payment_options\": \"" + transaction.getPayment_options() + "\",\n    \"order_ref\": \"" + transaction.getOrder_ref() + "\",\n    \"items\": " + transaction.getItems() + ",\n    \"cart\": [\n        {\n            \"product_name\": \"" + transaction.getProduct_name() + "\",\n            \"product_code\": \"" + transaction.getProduct_code() + "\",\n            \"quantity\": " + transaction.getQuantity() + ",\n            \"price\": \"" + transaction.getPrice() + "\",\n            \"total\": \"" + transaction.getTotal() + "\",\n            \"image\": \"\",\n            \"description\": \"\",\n            \"note_1\": \"\",\n            \"note_2\": \"\",\n            \"note_3\": \"\",\n            \"note_4\": \"\",\n            \"note_5\": \"\"\n        }]\n    }");
        Request request = new Request.Builder()
                .url("https://api.paygate.africa/transactions")
//                .method("POST", body)
                .addHeader("app_id", "$2a$10$wpM4MSm.Eu1gH.04Wz0YtO") //dont change
                .addHeader("refresh_token", "$2a$10$afjTJVYNmIgVg.U1OTdIaOyUhLEMnbrHxm3DpQcUAblaJizU.SViC") //dont change
                .addHeader("Authorization", Token)
                .addHeader("mode","dev")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "pga_session_id=s%3Aw05PKI6cBTmy-Fqm1qPJHj32hwVlXggg.clzIIgVNHDkRLzQbTCxDWYx1RsDS2r7XdoxbTHDhvU4")
                .build();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()){
            //System.out.println(response.body().string());
            System.out.println("************** Status reponse not Success ************* ");
            //System.out.println("************** Response body "+response.body().byteStream());
            return response.body().string();
        }
        return response.body().string();
    }
//
//    /**
//     * Update Tables after transaction Payment Callback
//     * @param transaction_id
//     * @param currency
//     * @param channel
//     * @param state
//     */
    public void UpdateTableAfterTransactionCallBack(String transaction_id,String currency,String channel,String state){
        transactionRepository.setTransactionStateByTransactionId(transaction_id, currency, channel, state);
        System.out.println("Get Commande by Transaction ID");
        Transaction transactionGet = transactionRepository.findTransactionByTransaction_id(transaction_id);
//        System.out.println("Id Commande recuperer : "+transactionGet.getIdCommande());
//        commandeRepository.setCommandeStateByCommandeId(state,transactionGet.getIdCommande());
        System.out.println("******************* After Update ************************");
    }

    @PostMapping(value = "test",produces = "application/json")
    public String testGetArray(@RequestBody TransactionWraper transactionWraper){
        for(Cart cart: transactionWraper.getCart()){
            System.out.println("******* cart");
            System.out.println(cart.getProduct_name());
        }
        return "ok";
    }
}

