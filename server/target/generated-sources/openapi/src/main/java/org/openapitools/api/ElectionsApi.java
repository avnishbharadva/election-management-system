/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapitools.api;

import org.openapitools.model.ElectionDTO;
import org.openapitools.model.ElectionPageResponse;
import org.openapitools.model.ModelApiResponse;
import org.openapitools.model.ValidationErrorResponseDTO;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-19T15:40:47.427401700+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
@Validated
@Tag(name = "Election", description = "the Election API")
public interface ElectionsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /elections : Create a new Election
     *
     * @param electionDTO  (required)
     * @return Election Created Successfully (status code 200)
     */
    @Operation(
        operationId = "createElection",
        summary = "Create a new Election",
        tags = { "Election" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Election Created Successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ModelApiResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/elections",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<ModelApiResponse> createElection(
        @Parameter(name = "ElectionDTO", description = "", required = true) @Valid @RequestBody ElectionDTO electionDTO
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : \"{}\", \"success\" : true, \"message\" : \"Success\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /elections/{electionId} : Delete an Election
     *
     * @param electionId Unique ID of the election (required)
     * @return Election deleted successfully (status code 200)
     *         or Election not found (status code 404)
     */
    @Operation(
        operationId = "deleteElection",
        summary = "Delete an Election",
        tags = { "Election" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Election deleted successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ModelApiResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Election not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/elections/{electionId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<ModelApiResponse> deleteElection(
        @Parameter(name = "electionId", description = "Unique ID of the election", required = true, in = ParameterIn.PATH) @PathVariable("electionId") Long electionId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : \"{}\", \"success\" : true, \"message\" : \"Success\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /elections : Get all Elections
     *
     * @return List of all elections retrieved successfully (status code 200)
     */
    @Operation(
        operationId = "getAllElections",
        summary = "Get all Elections",
        tags = { "Election" },
        responses = {
            @ApiResponse(responseCode = "200", description = "List of all elections retrieved successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ModelApiResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/elections",
        produces = { "application/json" }
    )
    
    default ResponseEntity<ModelApiResponse> getAllElections(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : \"{}\", \"success\" : true, \"message\" : \"Success\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /elections/{electionId} : Get Election by ID
     *
     * @param electionId Unique ID of the election (required)
     * @return Election retrieved successfully (status code 200)
     *         or Election not found (status code 404)
     */
    @Operation(
        operationId = "getElectionById",
        summary = "Get Election by ID",
        tags = { "Election" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Election retrieved successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ModelApiResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Election not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/elections/{electionId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<ModelApiResponse> getElectionById(
        @Parameter(name = "electionId", description = "Unique ID of the election", required = true, in = ParameterIn.PATH) @PathVariable("electionId") Long electionId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : \"{}\", \"success\" : true, \"message\" : \"Success\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /elections/sorted : Get sorted List of elections
     *
     * @param order Sort Order (asc/desc) (optional, default to asc)
     * @param page Page number for pagination (optional, default to 0)
     * @param size Number of records per page (optional, default to 5)
     * @return Paginated and sorted election list (status code 200)
     *         or Invalid request parameters (status code 400)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
        operationId = "getSortedElection",
        summary = "Get sorted List of elections",
        tags = { "Election" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Paginated and sorted election list", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ElectionPageResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/elections/sorted",
        produces = { "application/json" }
    )
    
    default ResponseEntity<ElectionPageResponse> getSortedElection(
        @Parameter(name = "order", description = "Sort Order (asc/desc)", in = ParameterIn.QUERY) @Valid @RequestParam(value = "order", required = false, defaultValue = "asc") String order,
        @Parameter(name = "page", description = "Page number for pagination", in = ParameterIn.QUERY) @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @Parameter(name = "size", description = "Number of records per page", in = ParameterIn.QUERY) @Valid @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"totalRecords\" : 100, \"perPage\" : 10, \"totalPages\" : 5, \"currentPage\" : 1, \"elections\" : [ { \"electionId\" : 1, \"electionName\" : \"Presidential Election\", \"electionDate\" : \"2025-10-10\", \"electionState\" : \"California\", \"electionType\" : \"General\", \"totalSeats\" : 100 }, { \"electionId\" : 1, \"electionName\" : \"Presidential Election\", \"electionDate\" : \"2025-10-10\", \"electionState\" : \"California\", \"electionType\" : \"General\", \"totalSeats\" : 100 } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /elections/{electionId} : Update Election Details
     *
     * @param electionId Unique ID of the Election (required)
     * @param electionDTO  (required)
     * @return Election Updated Successfully (status code 200)
     *         or Invalid request data (status code 400)
     *         or Election not found (status code 404)
     */
    @Operation(
        operationId = "updateElection",
        summary = "Update Election Details",
        tags = { "Election" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Election Updated Successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ModelApiResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Election not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/elections/{electionId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<ModelApiResponse> updateElection(
        @Parameter(name = "electionId", description = "Unique ID of the Election", required = true, in = ParameterIn.PATH) @PathVariable("electionId") Long electionId,
        @Parameter(name = "ElectionDTO", description = "", required = true) @Valid @RequestBody ElectionDTO electionDTO
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : \"{}\", \"success\" : true, \"message\" : \"Success\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"errors\" : [ \"errors\", \"errors\" ], \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
