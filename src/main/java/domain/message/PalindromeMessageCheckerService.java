package domain.message;


public class PalindromeMessageCheckerService {
    MessageRepository messageRepository;

    public PalindromeMessageCheckerService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void createMessage(Message message){
        messageRepository.save(message);
    }
//
//    public void findMessageById(String messageId) {
//        messageRepository.sendHolidayHoursUpdatedEmail(holidayHours);
//    }
//
//    public void findAllMessages(String messageId) {
//        messageRepository.sendHolidayHoursUpdatedEmail(holidayHours);
//    }
//
//    public void updateMessage(String messageId) {
//        messageRepository.sendHolidayHoursUpdatedEmail(holidayHours);
//    }
//
//    public void deleteMessage(String messageId) {
//        messageRepository.sendHolidayHoursUpdatedEmail(holidayHours);
//    }
//
//    private boolean isPalindromeMessage(){
//
//    }




}
