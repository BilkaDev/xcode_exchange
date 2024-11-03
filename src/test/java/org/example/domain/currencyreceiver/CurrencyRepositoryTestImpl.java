package org.example.domain.currencyreceiver;

import lombok.Getter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Getter
public class CurrencyRepositoryTestImpl implements CurrencyRepository {
    private final Map<String, Currency> currencyMap = new ConcurrentHashMap<>();

    @Override
    public List<Currency> findAll() {
        return new ArrayList<>(currencyMap.values());
    }


    @Override
    public Currency save(Currency currency) {
        currencyMap.put(String.valueOf(currencyMap.size()), currency);
        return null;
    }

    @Override
    public <S extends Currency> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Currency> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }


    @Override
    public List<Currency> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Currency entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Currency> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Currency> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Currency> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Currency> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Currency getOne(Long aLong) {
        return null;
    }

    @Override
    public Currency getById(Long aLong) {
        return null;
    }

    @Override
    public Currency getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Currency> List<S> findAll(Example<S> example) {
        return List.of();
    }


    @Override
    public <S extends Currency> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }


    @Override
    public <S extends Currency> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Currency> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Currency> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Currency> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Currency, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Currency> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Currency> findAll(Pageable pageable) {
        return null;
    }
}
