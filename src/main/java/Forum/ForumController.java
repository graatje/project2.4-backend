package Forum;

import Exceptions.ThreadNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class ForumController {

    private final ForumRepository repository;
    private final ForumThreadModelAssembler assembler;

    ForumController(ForumRepository repository, ForumThreadModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("studiekamer/prikbord")
    CollectionModel<EntityModel<ForumThread>> getAllThreads() {
        List<EntityModel<ForumThread>> threads = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(threads,
                linkTo(methodOn(ForumController.class).getAllThreads()).withSelfRel());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("studiekamer/prikbord")
    ForumThread newThread(@RequestBody ForumThread thread) {
        return repository.save(thread);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("studiekamer/prikbord/{id}")
    EntityModel<ForumThread> getThread(@PathVariable Long id) {
        ForumThread thread = repository.findById(id).orElseThrow(() -> new ThreadNotFoundException(id));

        return assembler.toModel(thread);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("studiekamer/prikbord/{id}")
    EntityModel<ForumThread> replaceThread( @PathVariable Long id, @RequestBody ForumThread newThread) {
        return assembler.toModel(repository.findById(id).map(thread -> {
            thread.setAuthor(newThread.getAuthor());
            thread.setTitle(newThread.getTitle());
            thread.setReplies(newThread.getReplies());
            thread.setContent(newThread.getContent());
            return repository.save(thread);
        })
                .orElseGet(() -> {
                    newThread.setId(id);
                    return repository.save(newThread);
                }));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("studiekamer/prikbord/{id}")
    void deleteThread(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
