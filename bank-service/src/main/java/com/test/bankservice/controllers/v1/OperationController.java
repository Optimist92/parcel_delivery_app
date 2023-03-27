package com.test.bankservice.controllers.v1;

import com.test.bankservice.domain.User;
import com.test.bankservice.payload.operations.CardReservePayload;
import com.test.bankservice.services.IOperationFacade;
import com.test.bankservice.services.IUserService;
import com.test.bankservice.services.impl.OperationFacade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/v1/operations")
public class OperationController {

    //private final AccountService accountService;
    private final IOperationFacade operationFacade;

    private final IUserService userService;

    @PostMapping(path="/reserve-from-card")
    //public UUID reserveAmountByCardNumber(@CurrentSecurityContext(expression="authentication") Authentication authentication, CardReservePayload payload) {
    public UUID reserveAmountByCardNumber(@RequestBody CardReservePayload payload) {

        /*
        if (!authentication.isAuthenticated())
            throw new RuntimeException("not authenticated");
*/

        var initiator = userService.getAllContractors().stream().findFirst().orElseThrow(() -> new RuntimeException("no contractor found"));

        return operationFacade.createReservationWithCard(initiator,
                        payload.getCardNumber(),
                        payload.getAmount())
                .getPublicId();
    }
}
