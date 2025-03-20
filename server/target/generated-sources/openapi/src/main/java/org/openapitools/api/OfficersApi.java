/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapitools.api;

import org.openapitools.model.AuthResponseDTO;
import org.openapitools.model.LoginForm;
import org.openapitools.model.OfficersRegisterDTO;
import org.openapitools.model.OfficersResponseDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-20T11:44:06.360579100+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
@Validated
@Tag(name = "Authentication", description = "the Authentication API")
public interface OfficersApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /officers/authenticate : Authenticate user and get JWT token
     * Authenticates a user with email and password and returns a JWT token.
     *
     * @param loginForm  (required)
     * @return Successful authentication, returns a JWT token (status code 200)
     *         or Bad Request - Invalid input data (status code 400)
     *         or Unauthorized - Invalid credentials (status code 401)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
        operationId = "authenticateUser",
        summary = "Authenticate user and get JWT token",
        description = "Authenticates a user with email and password and returns a JWT token.",
        tags = { "Authentication" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful authentication, returns a JWT token", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/officers/authenticate",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<AuthResponseDTO> authenticateUser(
        @Parameter(name = "LoginForm", description = "", required = true) @Valid @RequestBody LoginForm loginForm
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"User authentication successful\", \"token\" : \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /officers : Get all officers
     * Retrieves a list of all officers.
     *
     * @return Successful response with a list of officers (status code 200)
     *         or Internal Server Error - Unable to fetch roles (status code 500)
     */
    @Operation(
        operationId = "getAllOfficers",
        summary = "Get all officers",
        description = "Retrieves a list of all officers.",
        tags = { "Officers" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful response with a list of officers", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OfficersResponseDTO.class)))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Unable to fetch roles")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/officers",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<OfficersResponseDTO>> getAllOfficers(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"password\" : \"SecurePass123\", \"role\" : \"Admin\", \"ssnNumber\" : \"123456789\", \"officerId\" : 101, \"email\" : \"officer@example.com\" }, { \"password\" : \"SecurePass123\", \"role\" : \"Admin\", \"ssnNumber\" : \"123456789\", \"officerId\" : 101, \"email\" : \"officer@example.com\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /officers : Register a new officer
     * Registers a new officer in the system.
     *
     * @param officersRegisterDTO  (required)
     * @return Officer registered successfully (status code 201)
     *         or Bad Request - Invalid data provided (status code 400)
     *         or Conflict - Data Already Exists (status code 409)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
        operationId = "registerOfficer",
        summary = "Register a new officer",
        description = "Registers a new officer in the system.",
        tags = { "Officers" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Officer registered successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = OfficersRegisterDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid data provided"),
            @ApiResponse(responseCode = "409", description = "Conflict - Data Already Exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/officers",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<OfficersRegisterDTO> registerOfficer(
        @Parameter(name = "OfficersRegisterDTO", description = "", required = true) @Valid @RequestBody OfficersRegisterDTO officersRegisterDTO
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"password\" : \"SecurePass123\", \"role\" : \"Admin\", \"ssnNumber\" : \"123-45-6789\", \"email\" : \"officer@example.com\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
