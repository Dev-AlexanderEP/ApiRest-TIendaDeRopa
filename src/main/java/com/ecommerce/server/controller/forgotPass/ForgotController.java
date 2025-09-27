package com.ecommerce.server.controller.forgotPass;
import com.ecommerce.server.service.forgotPass.ForgotCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// controller
@RestController
@RequestMapping("/api/v1")
public class ForgotController {

    // dto
    public record VerifyCodeRequest(String email, String code) {}
    public record ApiResult(boolean ok, String message) {}

    private final ForgotCodeService service;

    public ForgotController(ForgotCodeService service) {
        this.service = service;
    }

    @PostMapping("/verificar-codigo")
    public ResponseEntity<ApiResult> verify(@RequestBody VerifyCodeRequest req) {
        boolean ok = service.verifyAndInvalidate(req.email(), req.code());
        if (!ok) return ResponseEntity.status(400).body(new ApiResult(false, "Invalid or expired code"));
        return ResponseEntity.ok(new ApiResult(true, "Valid code"));
    }

}

