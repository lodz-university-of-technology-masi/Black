package pl.masi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.masi.dto.DatamuseResponseDto;
import pl.masi.exception.MasiException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SynonymService {

    @Value("${synonym.api.url}")
    private String synonymApiUrl;

    @Value("${synonym.api.results.max}")
    private Integer maxSynonymsNumber;

    private RestTemplate restTemplate = new RestTemplate();

    @PreAuthorize("hasRole('ROLE_MODERATOR') || hasRole('ROLE_REDACTOR')")
    public List<String> findSynonyms(String word) {
        ResponseEntity<DatamuseResponseDto[]> resp =
                restTemplate.getForEntity(synonymApiUrl + "/words?ml={word}&max={max}",
                DatamuseResponseDto[].class,
                word,
                this.maxSynonymsNumber);
        if (resp.getStatusCode().isError()) {
            throw new MasiException("Cannot find synonyms!");
        }
        DatamuseResponseDto[] body = resp.getBody();
        if (body == null) {
            throw new MasiException("Unexpected API response");
        }
        return Arrays.stream(body).map(DatamuseResponseDto::getWord).collect(Collectors.toList());
    }
}
