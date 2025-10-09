package com.ecommerce.server.controller.forgotPass;
import com.ecommerce.server.service.forgotPass.ForgotCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// controller
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:4200",
        "http://localhost:5174",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe",
        "*"
})
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

