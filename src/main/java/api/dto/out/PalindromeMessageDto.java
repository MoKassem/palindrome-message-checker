package api.dto.out;

import domain.message.Message;

public class PalindromeMessageDto {

    public final Integer number;
    public final String content;
    public final boolean isPalindrome;

    public PalindromeMessageDto(Integer number, String content, boolean isPalindrome) {
        this.number = number;
        this.content = content;
        this.isPalindrome = isPalindrome;
    }

    public static PalindromeMessageDto fromDomain(Message message) {
        return new PalindromeMessageDto(message.messageNumber(), message.content(), message.ispalindrome());
    }
}

