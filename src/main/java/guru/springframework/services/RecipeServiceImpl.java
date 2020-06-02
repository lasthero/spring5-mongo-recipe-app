package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");

        return recipeRepository.findAll();
        //Set<Recipe> recipeSet = new HashSet<>();
        //recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        //return recipeSet;
    }

    @Override
    public Mono<Recipe> findById(String id) {

        return recipeRepository.findById(id);
        //Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        //if (!recipeOptional.isPresent()) {
//            throw new NotFoundException("Recipe Not Found. For ID value: " + id );
//        }
//
//        return recipeOptional.get();
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return findById(id).map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        return recipeRepository.save(detachedRecipe)
            .map(recipeToRecipeCommand::convert);
//        log.debug("Saved RecipeId:" + savedRecipe.getId());
//        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public Mono<Void> deleteById(String idToDelete) {
        return recipeRepository.deleteById(idToDelete);
    }
}
