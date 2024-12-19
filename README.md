Brewery Search API Test Suite
This repository contains the automated test suite for the Brewery Search API, which allows users to search for breweries
based on a search term. The tests are written using the REST-assured library for API testing in Java.

Existing Tests

1. Schema Validation
   Validates that the API response adheres to the defined schema (brewery-schema.json).
2. Search Functionality
   Tests the search results for various query terms:
   Valid queries (e.g., "san diego")
   Edge cases (e.g., special characters, numbers, long queries with no results)
   Empty queries
3. Pagination
   Verifies the API's pagination behavior by varying the per_page parameter:
   Valid numbers (e.g., 1, 3, maximum number of entries)
   Invalid inputs (e.g., negative numbers, non-numeric values)
4. Response Field Validation
   Ensures that the response contains all expected fields (id, name, brewery_type, city, etc.) and that these fields are
   not null.
5. Specific Brewery Search
   Validates that the API can search for a specific brewery name (e.g., "Mikkeller Brewing San Diego") and return
   accurate results.
6. Missing Query Parameter
   Tests the behavior when the query parameter is omitted (should return an empty array or appropriate response).

How to Run Tests
Clone the repository.
Ensure that Maven and Java are installed.
Run the test suite using the following command: mvn test

Potential Additional Tests
To enhance coverage for the "Search for Breweries" API, the following tests can be added:

Authorization and Security Tests

Test endpoint behavior with missing or invalid API keys (if applicable).

Validate the response headers for security best practices (e.g., Content-Security-Policy, X-Content-Type-Options).

Performance and Load Testing

Measure response time for various query sizes and parameters (e.g., query=san diego&per_page=100).

Perform load testing with multiple concurrent requests to assess API stability.

Localization Testing

Test the API's ability to handle queries with international characters (e.g., "München", "Łódź").

Ensure consistent behavior across different locales.

Negative Test Cases

Inject malicious inputs (e.g., SQL injection or XSS payloads) into the query parameter to verify API security.

Test with overly large input values to check for proper error handling.

Test for idempotency by sending the same request multiple times and verifying identical responses.

Test for caching

Dependency Tests

Validate API behavior when dependent services (e.g., database) are down or unresponsive.

Test for graceful degradation and error handling (e.g., HTTP 503 with retry logic).

Test Automation for the "List Breweries" Method

Validate Response Schema: Ensure the response matches the expected JSON schema.

Validate Pagination:

Different per_page values (e.g., 1, 50, 100).

Boundary values for per_page (e.g., 0, -1, Integer.MAX_VALUE).

Non-integer per_page values (e.g., null, "abc").

Validate Default Pagination: Verify the default number of results when per_page is not specified.

Validate Response Fields: Check that all required fields are present and not null.

Validate Invalid Query Parameters: Test the response for invalid query parameters (e.g., invalidParam=test).

Validate Special Characters in Query: Test search terms with special characters and ensure proper handling.

Validate Large Pagination Value: Ensure the API handles very high per_page values correctly.

Validate Empty Result Set: Check the response when no breweries match the criteria.

Validate Response Time: Ensure the API responds within an acceptable time frame.

Validate HTTP Methods: Ensure the endpoint only accepts GET requests and handles other HTTP methods appropriately (e.g., POST, PUT, DELETE).

Validate Filter by Brewery Type: Test filtering results by different brewery types (e.g., micro, brewpub).

Validate Sorting: Check if the results can be sorted by specific fields (e.g., name, city).

Validate Response for Specific Fields: Ensure the API returns correct data for fields like longitude and latitude.

Validate Country Filtering: Test filtering results by different countries.

Validate State Filtering: Test filtering results by different states/provinces.
