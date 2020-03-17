package com.hedvig.backoffice.services.messages.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class BackOfficeResponseDTO {

    public String memberId;
    public String userId;

    @NotNull
    public String msg;

    public BackOfficeResponseDTO(String memberId, String msg) {
        this.memberId = memberId;
        this.userId = memberId;
        this.msg = msg;
    }

}
