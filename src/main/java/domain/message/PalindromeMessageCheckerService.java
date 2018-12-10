package domain.message;


import com.mokassem.exceptions.EntityNotFoundException;
import domain.ReadContext;

import java.util.Collections;
import java.util.Set;

public class PalindromeMessageCheckerService {
    private final MessageRepository messageRepository;

    public PalindromeMessageCheckerService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message findMessageById(ReadContext context, String messageId) {
        return messageRepository.findById(context, messageId)
                                .orElseThrow(() -> new EntityNotFoundException("Message with id: " + messageId + " is not found."));
    }

    public void createMessage(Message message, boolean ignoreCaseAndPunct) {
        boolean isPalindrome = this.isPalindrome(message.content(), ignoreCaseAndPunct);
        message = message.toBuilder().ispalindrome(isPalindrome).build();
        messageRepository.save(message);
    }

    public Set<Message> find(ReadContext context, MessageQuery query) {
        return messageRepository.find(context, query);
    }

    public void updateMessage(String messageId, Message updatedMessage, boolean isCaseAndPunct) {

        Message existingMessage = this.findMessageById(ReadContext.SYNC_REQUIRED, messageId);
        boolean palindrome = this.isPalindrome(updatedMessage.content(), isCaseAndPunct);
        updatedMessage = existingMessage.toBuilder()
                                        .content(updatedMessage.content())
                                        .ispalindrome(palindrome)
                                        .createdTime(updatedMessage.createdTime()).build();
        messageRepository.update(messageId, updatedMessage);
    }

    public void deleteMessage(String messageId) {
        Message existingMessage = this.findMessageById(ReadContext.SYNC_REQUIRED, messageId);
        messageRepository.removeByIds(Collections.singleton(existingMessage.messageId()));
    }


    boolean isPalindrome(String message, boolean ignoreCaseAndPunct) {
        message = ignoreCaseAndPunct ? message.replaceAll("[^a-zA-Z]", "").toLowerCase() : message;
        int start = 0;
        int end = message.length()-1;
        int middle = (start + end) / 2;

        while (start < middle) {
            if (message.charAt(start++) != message.charAt(end--)) {
                return false;
            }
        }
        return true;
    }
}
