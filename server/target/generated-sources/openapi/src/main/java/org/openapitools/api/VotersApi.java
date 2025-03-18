/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapitools.api;

import org.openapitools.model.AuditDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.model.ErrorResponse;
import java.time.LocalDate;
import org.openapitools.model.PaginatedVoterDTO;
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterStatusDTO;
import org.openapitools.model.VoterUpdateRequest;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-13T18:26:17.448530600+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
@Validated
@Tag(name = "Voter", description = "the Voter API")
public interface VotersApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /voters/status : Get all voter statuses
     *
     * @return Successfully retrieved voter statuses (status code 200)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
        operationId = "getAllStatus",
        summary = "Get all voter statuses",
        tags = { "Voter" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved voter statuses", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VoterStatusDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/voters/status",
        produces = { "application/json" }
    )
    
    default ResponseEntity<VoterStatusDTO> getAllStatus(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"msg\" : \"Success\", \"data\" : [ { \"statusDesc\" : \"Active\", \"statusId\" : 1 }, { \"statusDesc\" : \"Active\", \"statusId\" : 1 } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /voters/audit/{voterId} : Get Voter Audit by Voter Id
     *
     * @param voterId Unique ID of the Voter (required)
     * @return Audit Details retrieved successfully (status code 200)
     *         or Voter not found (status code 404)
     */
    @Operation(
        operationId = "getAudit",
        summary = "Get Voter Audit by Voter Id",
        tags = { "Voter" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Audit Details retrieved successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AuditDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Voter not found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/voters/audit/{voterId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<AuditDTO> getAudit(
        @Parameter(name = "voterId", description = "Unique ID of the Voter", required = true, in = ParameterIn.PATH) @PathVariable("voterId") String voterId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"createdAt\" : \"2025-03-11T13:07:06.27Z\", \"updatedBy\" : \"ADMIN\", \"oldFields\" : { \"firstName\" : \"John\", \"lastName\" : \"Doe\" }, \"createdBy\" : \"SYSTEM\", \"changeFields\" : { \"firstName\" : \"Mark\", \"lastName\" : \"Smith\" }, \"voterId\" : \"000000123\", \"id\" : \"65fd3a2e5b6c\", \"tableName\" : \"voter\", \"updatedAt\" : \"2025-03-11T15:22:30.12Z\" }, { \"createdAt\" : \"2025-03-11T13:07:06.27Z\", \"updatedBy\" : \"ADMIN\", \"oldFields\" : { \"firstName\" : \"John\", \"lastName\" : \"Doe\" }, \"createdBy\" : \"SYSTEM\", \"changeFields\" : { \"firstName\" : \"Mark\", \"lastName\" : \"Smith\" }, \"voterId\" : \"000000123\", \"id\" : \"65fd3a2e5b6c\", \"tableName\" : \"voter\", \"updatedAt\" : \"2025-03-11T15:22:30.12Z\" } ], \"message\" : \"Success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"Detailed error message\", \"timestamp\" : \"10:30:15\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /voters : Register a new voter
     * Registers a new voter with the provided details.
     *
     * @param voterRegisterDTO  (required)
     * @return Voter successfully registered (status code 201)
     *         or Invalid request data (status code 400)
     *         or Voter already exists (status code 409)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "registerVoter",
        summary = "Register a new voter",
        description = "Registers a new voter with the provided details.",
        tags = { "Voter" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Voter successfully registered", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VoterDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Voter already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/voters",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<VoterDTO> registerVoter(
        @Parameter(name = "VoterRegisterDTO", description = "", required = true) @Valid @RequestBody VoterRegisterDTO voterRegisterDTO
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"lastName\" : \"Doe\", \"image\" : \"base64-encoded-image-string\", \"gender\" : \"MALE\", \"signature\" : \"base64-encoded-signature-string\", \"ssnNumber\" : \"987654321\", \"dateOfBirth\" : \"1990-05-15\", \"dmvNumber\" : \"123456789\", \"firstName\" : \"John\", \"phoneNumber\" : \"12345678901\", \"firstVotedYear\" : 2010, \"mailingAddress\" : { \"zipCode\" : \"62704\", \"city\" : \"Springfield\", \"addressType\" : \"RESIDENTIAL\", \"county\" : \"Clark\", \"state\" : \"New York\", \"addressLine\" : \"123 Main St\", \"aptNumber\" : \"Apt 4B\" }, \"residentialAddress\" : { \"zipCode\" : \"62704\", \"city\" : \"Springfield\", \"addressType\" : \"RESIDENTIAL\", \"county\" : \"Clark\", \"state\" : \"New York\", \"addressLine\" : \"123 Main St\", \"aptNumber\" : \"Apt 4B\" }, \"voterId\" : \"000123456\", \"suffixName\" : \"Jr.\", \"middleName\" : \"A.\", \"email\" : \"john.doe@example.com\", \"party\" : \"Democratic Party\", \"hasVotedBefore\" : false, \"status\" : \"Active\" }, \"message\" : \"Success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /voters : Search voters with filters and pagination
     * Search for voters based on various filter criteria such as first name, last name, date of birth, etc.
     *
     * @param page The page number to retrieve. (required)
     * @param size The number of voters per page. (required)
     * @param firstName The first name of the voter. (optional)
     * @param lastName The last name of the voter. (optional)
     * @param dateOfBirth The date of birth of the voter. (optional)
     * @param dmvNumber The DMV number of the voter. (optional)
     * @param ssnNumber The SSN number of the voter. (optional)
     * @param city The city where the voter resides. (optional)
     * @param sort Sorting order of the results. Use multiple &#x60;sort&#x60; parameters to specify multiple fields (e.g., &#x60;sort&#x3D;firstName,asc&amp;sort&#x3D;lastName,desc&#x60;). (optional)
     * @return Successfully retrieved paginated voter list (status code 200)
     *         or Invalid request parameters (status code 400)
     *         or Server error (status code 500)
     */
    @Operation(
        operationId = "searchVoters",
        summary = "Search voters with filters and pagination",
        description = "Search for voters based on various filter criteria such as first name, last name, date of birth, etc.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated voter list", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedVoterDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Server error")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/voters",
        produces = { "application/json" }
    )
    
    default ResponseEntity<PaginatedVoterDTO> searchVoters(
        @NotNull @Parameter(name = "page", description = "The page number to retrieve.", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
        @NotNull @Parameter(name = "size", description = "The number of voters per page.", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "size", required = true, defaultValue = "10") Integer size,
        @Parameter(name = "firstName", description = "The first name of the voter.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "firstName", required = false) String firstName,
        @Parameter(name = "lastName", description = "The last name of the voter.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "lastName", required = false) String lastName,
        @Parameter(name = "dateOfBirth", description = "The date of birth of the voter.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "dateOfBirth", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
        @Parameter(name = "dmvNumber", description = "The DMV number of the voter.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "dmvNumber", required = false) String dmvNumber,
        @Parameter(name = "ssnNumber", description = "The SSN number of the voter.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "ssnNumber", required = false) String ssnNumber,
        @Parameter(name = "city", description = "The city where the voter resides.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "city", required = false) String city,
        @Parameter(name = "sort", description = "Sorting order of the results. Use multiple `sort` parameters to specify multiple fields (e.g., `sort=firstName,asc&sort=lastName,desc`).", in = ParameterIn.QUERY) @Valid @RequestParam(value = "sort", required = false) List<String> sort
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"number\" : 5, \"data\" : [ { \"lastName\" : \"Doe\", \"image\" : \"base64-encoded-image-string\", \"gender\" : \"MALE\", \"signature\" : \"base64-encoded-signature-string\", \"ssnNumber\" : \"987654321\", \"dateOfBirth\" : \"1990-05-15\", \"dmvNumber\" : \"123456789\", \"firstName\" : \"John\", \"phoneNumber\" : \"12345678901\", \"firstVotedYear\" : 2010, \"mailingAddress\" : { \"zipCode\" : \"62704\", \"city\" : \"Springfield\", \"addressType\" : \"RESIDENTIAL\", \"county\" : \"Clark\", \"state\" : \"New York\", \"addressLine\" : \"123 Main St\", \"aptNumber\" : \"Apt 4B\" }, \"residentialAddress\" : { \"zipCode\" : \"62704\", \"city\" : \"Springfield\", \"addressType\" : \"RESIDENTIAL\", \"county\" : \"Clark\", \"state\" : \"New York\", \"addressLine\" : \"123 Main St\", \"aptNumber\" : \"Apt 4B\" }, \"voterId\" : \"000123456\", \"suffixName\" : \"Jr.\", \"middleName\" : \"A.\", \"email\" : \"john.doe@example.com\", \"party\" : \"Democratic Party\", \"hasVotedBefore\" : false, \"status\" : \"Active\" }, { \"lastName\" : \"Doe\", \"image\" : \"base64-encoded-image-string\", \"gender\" : \"MALE\", \"signature\" : \"base64-encoded-signature-string\", \"ssnNumber\" : \"987654321\", \"dateOfBirth\" : \"1990-05-15\", \"dmvNumber\" : \"123456789\", \"firstName\" : \"John\", \"phoneNumber\" : \"12345678901\", \"firstVotedYear\" : 2010, \"mailingAddress\" : { \"zipCode\" : \"62704\", \"city\" : \"Springfield\", \"addressType\" : \"RESIDENTIAL\", \"county\" : \"Clark\", \"state\" : \"New York\", \"addressLine\" : \"123 Main St\", \"aptNumber\" : \"Apt 4B\" }, \"residentialAddress\" : { \"zipCode\" : \"62704\", \"city\" : \"Springfield\", \"addressType\" : \"RESIDENTIAL\", \"county\" : \"Clark\", \"state\" : \"New York\", \"addressLine\" : \"123 Main St\", \"aptNumber\" : \"Apt 4B\" }, \"voterId\" : \"000123456\", \"suffixName\" : \"Jr.\", \"middleName\" : \"A.\", \"email\" : \"john.doe@example.com\", \"party\" : \"Democratic Party\", \"hasVotedBefore\" : false, \"status\" : \"Active\" } ], \"size\" : 1, \"totalPages\" : 6, \"totalElements\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PATCH /voters/{voterId} : Update voter details
     * Updates a voter&#39;s details based on the provided voter ID.
     *
     * @param voterId Unique identifier of the voter (required)
     * @param voterUpdateRequest  (required)
     * @return Voter details updated successfully (status code 200)
     *         or Bad request, invalid input (status code 400)
     *         or Voter not found (status code 404)
     *         or Internal server error (status code 500)
     */
    @Operation(
        operationId = "votersVoterIdPatch",
        summary = "Update voter details",
        description = "Updates a voter's details based on the provided voter ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Voter details updated successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VoterDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input"),
            @ApiResponse(responseCode = "404", description = "Voter not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @RequestMapping(
        method = RequestMethod.PATCH,
        value = "/voters/{voterId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<VoterDTO> votersVoterIdPatch(
        @Parameter(name = "voterId", description = "Unique identifier of the voter", required = true, in = ParameterIn.PATH) @PathVariable("voterId") String voterId,
        @Parameter(name = "VoterUpdateRequest", description = "", required = true) @Valid @RequestBody VoterUpdateRequest voterUpdateRequest
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"lastName\" : \"Doe\", \"image\" : \"base64-encoded-image-string\", \"gender\" : \"MALE\", \"signature\" : \"base64-encoded-signature-string\", \"ssnNumber\" : \"987654321\", \"dateOfBirth\" : \"1990-05-15\", \"dmvNumber\" : \"123456789\", \"firstName\" : \"John\", \"phoneNumber\" : \"12345678901\", \"firstVotedYear\" : 2010, \"mailingAddress\" : { \"zipCode\" : \"62704\", \"city\" : \"Springfield\", \"addressType\" : \"RESIDENTIAL\", \"county\" : \"Clark\", \"state\" : \"New York\", \"addressLine\" : \"123 Main St\", \"aptNumber\" : \"Apt 4B\" }, \"residentialAddress\" : { \"zipCode\" : \"62704\", \"city\" : \"Springfield\", \"addressType\" : \"RESIDENTIAL\", \"county\" : \"Clark\", \"state\" : \"New York\", \"addressLine\" : \"123 Main St\", \"aptNumber\" : \"Apt 4B\" }, \"voterId\" : \"000123456\", \"suffixName\" : \"Jr.\", \"middleName\" : \"A.\", \"email\" : \"john.doe@example.com\", \"party\" : \"Democratic Party\", \"hasVotedBefore\" : false, \"status\" : \"Active\" }, \"message\" : \"Success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
