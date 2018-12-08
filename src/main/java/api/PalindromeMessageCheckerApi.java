package api;


import domain.message.PalindromeMessageCheckerService;
import api.dto.MyMessage;


public class PalindromeMessageCheckerApi {
    public final PalindromeMessageCheckerService palindromeMessageCheckerService;

    public PalindromeMessageCheckerApi(PalindromeMessageCheckerService palindromeMessageCheckerService) {
        this.palindromeMessageCheckerService = palindromeMessageCheckerService;
    }

    public void createMessage(MyMessage message){
        palindromeMessageCheckerService.createMessage(message.toDomain());
    }
//
//    public void findMessageById(String messageId) {
//        palindromeMessageCheckerService.findMessageById(messageId);
//    }
//
//    public void findAllMessages(String messageId) {
//        palindromeMessageCheckerService.findAllMessages(messageId);
//    }
//
//    public void updateMessage(String messageId) {
//        palindromeMessageCheckerService.updateMessage(messageId);
//    }
//
//    public void deleteMessage(String messageId) {
//        palindromeMessageCheckerService.deleteMessage();
//    }








}
