/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapitools.api;

import org.openapitools.model.ErrorResponse;
import org.openapitools.model.PartyDTO;
import org.openapitools.model.PartyListDTO;
import org.openapitools.model.PartyRegisterDTO;
import org.openapitools.model.PartyUpdateDTO;
import org.openapitools.model.ValidationErrorResponse;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-18T23:35:33.672383700+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
@Validated
@Tag(name = "Party", description = "the Party API")
public interface PartyApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /party : Create a new party
     *
     * @param partyRegisterDTO  (required)
     * @return Party created successfully (status code 200)
     *         or Invalid request data (status code 400)
     */
    @Operation(
        operationId = "createParty",
        summary = "Create a new party",
        tags = { "Party" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Party created successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PartyDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/party",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<PartyDTO> createParty(
        @Parameter(name = "PartyRegisterDTO", description = "", required = true) @Valid @RequestBody PartyRegisterDTO partyRegisterDTO
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"partyLeaderName\" : \"Narendra Modi\", \"partyAbbreviation\" : \"DA\", \"partyFoundationYear\" : 1998, \"headQuarters\" : \"Washington, D.C.\", \"partyName\" : \"Democratic Alliance\", \"partySymbol\" : \"partySymbol\", \"partyId\" : 1, \"partyWebSite\" : \"https://www.democraticalliance.org\", \"partyFounderName\" : \"John Smith\" }, \"message\" : \"Success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"Validation failed for request.\", \"errors\" : [ { \"field\" : \"partyName\", \"errorMessage\" : \"Party name must be between 3 and 30 characters.\" }, { \"field\" : \"partyName\", \"errorMessage\" : \"Party name must be between 3 and 30 characters.\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /party/{partyId} : Delete party details
     *
     * @param partyId Unique id of the party (required)
     * @return Party deleted successfully (status code 200)
     *         or Party not found (status code 404)
     */
    @Operation(
        operationId = "deleteParty",
        summary = "Delete party details",
        tags = { "Party" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Party deleted successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PartyDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Party not found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/party/{partyId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<PartyDTO> deleteParty(
        @Parameter(name = "partyId", description = "Unique id of the party", required = true, in = ParameterIn.PATH) @PathVariable("partyId") Long partyId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"partyLeaderName\" : \"Narendra Modi\", \"partyAbbreviation\" : \"DA\", \"partyFoundationYear\" : 1998, \"headQuarters\" : \"Washington, D.C.\", \"partyName\" : \"Democratic Alliance\", \"partySymbol\" : \"partySymbol\", \"partyId\" : 1, \"partyWebSite\" : \"https://www.democraticalliance.org\", \"partyFounderName\" : \"John Smith\" }, \"message\" : \"Success\" }";
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
     * GET /party : Get all parties
     *
     * @return List of all parties retrieved successfully (status code 200)
     */
    @Operation(
        operationId = "findAllParties",
        summary = "Get all parties",
        tags = { "Party" },
        responses = {
            @ApiResponse(responseCode = "200", description = "List of all parties retrieved successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PartyListDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/party",
        produces = { "application/json" }
    )
    
    default ResponseEntity<PartyListDTO> findAllParties(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"partyLeaderName\" : \"Narendra Modi\", \"partyAbbreviation\" : \"DA\", \"partyFoundationYear\" : 1998, \"headQuarters\" : \"Washington, D.C.\", \"partyName\" : \"Democratic Alliance\", \"partySymbol\" : \"partySymbol\", \"partyId\" : 1, \"partyWebSite\" : \"https://www.democraticalliance.org\", \"partyFounderName\" : \"John Smith\" }, { \"partyLeaderName\" : \"Narendra Modi\", \"partyAbbreviation\" : \"DA\", \"partyFoundationYear\" : 1998, \"headQuarters\" : \"Washington, D.C.\", \"partyName\" : \"Democratic Alliance\", \"partySymbol\" : \"partySymbol\", \"partyId\" : 1, \"partyWebSite\" : \"https://www.democraticalliance.org\", \"partyFounderName\" : \"John Smith\" } ], \"message\" : \"Success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /party/{partyId} : Get party by ID
     *
     * @param partyId Unique ID of the party (required)
     * @return Party retrieved successfully (status code 200)
     *         or Party not found (status code 404)
     */
    @Operation(
        operationId = "findByPartyId",
        summary = "Get party by ID",
        tags = { "Party" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Party retrieved successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PartyDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Party not found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/party/{partyId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<PartyDTO> findByPartyId(
        @Parameter(name = "partyId", description = "Unique ID of the party", required = true, in = ParameterIn.PATH) @PathVariable("partyId") Long partyId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"partyLeaderName\" : \"Narendra Modi\", \"partyAbbreviation\" : \"DA\", \"partyFoundationYear\" : 1998, \"headQuarters\" : \"Washington, D.C.\", \"partyName\" : \"Democratic Alliance\", \"partySymbol\" : \"partySymbol\", \"partyId\" : 1, \"partyWebSite\" : \"https://www.democraticalliance.org\", \"partyFounderName\" : \"John Smith\" }, \"message\" : \"Success\" }";
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
     * PATCH /party/{partyId} : Update party details
     *
     * @param partyId Unique ID of the party (required)
     * @param partyUpdateDTO  (required)
     * @return Party updated successfully (status code 200)
     *         or Invalid request data (status code 400)
     *         or Party not found (status code 404)
     */
    @Operation(
        operationId = "updateParty",
        summary = "Update party details",
        tags = { "Party" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Party updated successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PartyDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Party not found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PATCH,
        value = "/party/{partyId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<PartyDTO> updateParty(
        @Parameter(name = "partyId", description = "Unique ID of the party", required = true, in = ParameterIn.PATH) @PathVariable("partyId") Long partyId,
        @Parameter(name = "PartyUpdateDTO", description = "", required = true) @Valid @RequestBody PartyUpdateDTO partyUpdateDTO
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"partyLeaderName\" : \"Narendra Modi\", \"partyAbbreviation\" : \"DA\", \"partyFoundationYear\" : 1998, \"headQuarters\" : \"Washington, D.C.\", \"partyName\" : \"Democratic Alliance\", \"partySymbol\" : \"partySymbol\", \"partyId\" : 1, \"partyWebSite\" : \"https://www.democraticalliance.org\", \"partyFounderName\" : \"John Smith\" }, \"message\" : \"Success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"Validation failed for request.\", \"errors\" : [ { \"field\" : \"partyName\", \"errorMessage\" : \"Party name must be between 3 and 30 characters.\" }, { \"field\" : \"partyName\", \"errorMessage\" : \"Party name must be between 3 and 30 characters.\" } ] }";
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

}
