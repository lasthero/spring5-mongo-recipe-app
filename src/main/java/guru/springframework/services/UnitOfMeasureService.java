package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;

import java.util.Set;
import reactor.core.publisher.Flux;

/**
 * Created by jt on 6/28/17.
 */
public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAllUoms();
}
