package com.lauri.kood.movieapi.repository;

import com.lauri.kood.movieapi.entity.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    /**
     * Finds actors whose name contains the specified string (case-insensitive).
     * <p>
     * This method performs a partial, case-insensitive match on the actor's name field.
     * It's useful for implementing search functionality where users can find actors
     * by typing part of their name.
     * </p>
     * <p>
     * Example:
     * <pre>
     * Page<Actor> results = actorRepository.findByNameContainingIgnoreCase(
     *     "dicaprio",
     *     PageRequest.of(0, 20)
     * );
     * </pre>
     * </p>
     *
     * @param name the search string to match against actor names (partial match)
     * @param pageable pagination configuration including page number, size, and sort order
     * @return a {@link Page} of {@link Actor} entities matching the search criteria
     */
    Page<Actor> findByNameContainingIgnoreCase(String name, Pageable pageable);
    /**
     * Finds all actors who appeared in the movie with the specified ID.
     * <p>
     * This method traverses the many-to-many relationship between actors and movies,
     * retrieving actors who are associated with the given movie. Results are paginated
     * to handle large casts efficiently.
     * </p>
     * <p>
     * The query uses Spring Data JPA's property expression {@code movies.id} to
     * automatically join the {@code Actor} and {@code Movie} entities through their
     * many-to-many relationship.
     * </p>
     * <p>
     * Example:
     * <pre>
     * Page<Actor> cast = actorRepository.findByMovies_Id(
     *     1L,
     *     PageRequest.of(0, 10)
     * );
     * </pre>
     * </p>
     *
     * @param movieId the ID of the movie to find actors for
     * @param pageable pagination configuration including page number, size, and sort order
     * @return a {@link Page} of {@link Actor} entities who appeared in the specified movie
     * @throws jakarta.persistence.EntityNotFoundException if the movie ID doesn't exist
     *         (validation happens in the service layer)

     */
    Page<Actor> findByMovies_Id(Long movieId, Pageable pageable);
}
