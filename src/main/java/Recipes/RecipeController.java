package Recipes;

import Exceptions.RecipeNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class RecipeController {
    private final RecipeRepository repository;
    private final RecipeModelAssembler assembler;

    RecipeController(RecipeRepository repository, RecipeModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("keuken/recepten")
    CollectionModel<EntityModel<Recipe>> getAllRecipes(){
        System.out.println("Method called");
        List<EntityModel<Recipe>> recipes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(recipes,
                linkTo(methodOn(RecipeController.class).getAllRecipes()).withSelfRel());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("keuken/recepten/{id}")
    EntityModel<Recipe> getRecipe(@PathVariable Long id){
        Recipe recipe = repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
        return assembler.toModel(recipe);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("keuken/recepten")
    EntityModel<Recipe> newRecipe(@RequestBody Recipe recipe){
        return assembler.toModel(repository.save(recipe));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("keuken/recepten/{id}")
    EntityModel<Recipe> replaceRecipe(@PathVariable Long id, @RequestBody Recipe newRecipe){
        return assembler.toModel(repository.findById(id).map(recipe -> {
            recipe.setAantalPersonen(newRecipe.getAantalPersonen());
            recipe.setBereidingswijze(newRecipe.getBereidingswijze());
            recipe.setBereidingstijd(newRecipe.getBereidingstijd());
            recipe.setThumbsDown(newRecipe.getThumbsDown());
            recipe.setThumbsUp(newRecipe.getThumbsUp());
            recipe.setIngredienten(newRecipe.getIngredienten());

            return repository.save(recipe);
        }).orElseGet(() -> {
            newRecipe.setId(id);
            return repository.save(newRecipe);
        }));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("keuken/recepten/{id}")
    void deleteThread(@PathVariable Long id){
        repository.deleteById(id);
    }
}
