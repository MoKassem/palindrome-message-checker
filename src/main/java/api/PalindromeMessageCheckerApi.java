package api;

import api.dto.in.MessageDto;
import api.dto.out.PalindromeMessageDto;
import domain.ReadContext;
import domain.message.MessageQuery;
import domain.message.PalindromeMessageCheckerService;

import java.util.Set;
import java.util.stream.Collectors;


public class PalindromeMessageCheckerApi {
    private final PalindromeMessageCheckerService palindromeMessageCheckerService;

    public PalindromeMessageCheckerApi(PalindromeMessageCheckerService palindromeMessageCheckerService) {
        this.palindromeMessageCheckerService = palindromeMessageCheckerService;
    }

    public PalindromeMessageDto findMessageById(String messageId) {
        return PalindromeMessageDto.fromDomain(palindromeMessageCheckerService.findMessageById(ReadContext.SYNC_NOT_REQUIRED, messageId));
    }

    public void createMessage(MessageDto message, boolean ignoreCaseAndPunct) {
        palindromeMessageCheckerService.createMessage(message.toDomain(), ignoreCaseAndPunct);
    }

    public Set<PalindromeMessageDto> findMessages(MessageQuery query) {
        return palindromeMessageCheckerService.find(ReadContext.SYNC_NOT_REQUIRED, query)
                                       .stream()
                                       .map(PalindromeMessageDto::fromDomain)
                                       .collect(Collectors
                                                        .toSet());
    }

    public void updateMessage(String messageId, MessageDto updatedMessage) {
        palindromeMessageCheckerService.updateMessage(messageId, updatedMessage.toDomain(), updatedMessage.isIgnoreCaseAndPunct());
    }

    public void deleteMessage(String messageId) {
        palindromeMessageCheckerService.deleteMessage(messageId);
    }
}
