import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {
    @Autowired
    ClubRepository clubRepository;

    @Autowired
    private ClubService clubService;

    @GetMapping(value = "/clubs",
        produces = "application/json")
    public ResponseEntity<?> listAllClubs() {
        List<Club> myClubs = clubService.findAll();
        return new ResponseEntity<>(myClubs, HttpStatus.OK);
    }
}