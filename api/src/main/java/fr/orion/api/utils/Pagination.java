package fr.orion.api.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class Pagination<T> {

    private final List<Page> pages;
    private final List<T> elements;

    private int elementsPerPage;

    public Pagination(int elementsPerPage) {
        this.pages = new ArrayList<>();
        this.elements = new ArrayList<>();
        this.elementsPerPage = elementsPerPage;
        this.generatePages();
    }

    public Pagination(Collection<T> elements, int elementsPerPage) {
        this(elementsPerPage);
        elements.forEach(this::addElement);
    }

    public void addElement(T element) {
        getElements().add(element);

        if (getRequiredPages() > countPages()) {
            int index = getElements().size() - 1;
            getPages().add(new Page(this.countPages() + 1, index, index + getElementsPerPage()));
        }
    }

    public void removeElement(T element) {
        if (!getElements().contains(element)) return;

        getElements().remove(element);
        int pages = countPages();

        if (pages > 1 && getRequiredPages() < pages)
            getPages().remove(getLast());
    }

    public void removeElement(int index) {
        if (index < 0)
            throw new IllegalArgumentException("Index cannot be negative.");
        if (index >= getElements().size())
            throw new IllegalArgumentException(String.format("Index : %d Size: %d", index, this.countElements()));

        getElements().remove(index);
        int pages = countPages();

        if (pages > 1 && getRequiredPages() < pages)
            getPages().remove(getLast());
    }

    public boolean containsElement(T element) {
        return getElements().contains(element);
    }

    public int indexOf(T element) {
        return getElements().indexOf(element);
    }

    public Page getLast() {
        return getPages().get(getPages().size() - 1);
    }

    public Page getFirst() {
        return getPages().get(0);
    }

    public boolean contains(Page page) {
        return getPages().contains(page);
    }

    public boolean isLast(Page page) {
        return page.equals(getLast());
    }

    public boolean isFirst(Page page) {
        return page.equals(getFirst());
    }

    public boolean hasNext(Page page) {
        return !isLast(page);
    }

    public boolean hasPrevious(Page page) {
        return !isFirst(page);
    }

    public Page getNext(Page page) {
        return hasNext(page) ? getPages().get(getPages().indexOf(page) + 1) : null;
    }

    public Page getPrevious(Page page) {
        return hasPrevious(page) ? getPages().get(getPages().indexOf(page) - 1) : null;
    }

    public Page getPage(int number) {
        if (number < 1 || number > getPages().size())
            throw new IllegalArgumentException(String.format("Page number must be between 1 and %d (include).", getPages().size()));

        return getPages().get(number - 1);
    }

    public boolean hasPage(int number) {
        return number > 0 && number <= getPages().size();
    }

    public int countPages() {
        return getPages().size();
    }

    public void setElementsPerPage(int elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
        generatePages();
    }

    public int countElements() {
        return getElements().size();
    }

    private int getRequiredPages() {
        return (int) Math.ceil((double) getElements().size() / getElementsPerPage());
    }

    private void generatePages() {
        List<T> elements = new ArrayList<>(getElements());

        getPages().clear();
        getElements().clear();
        elements.forEach(this::addElement);

        if (getPages().size() == 0)
            getPages().add(new Page(1, 0, getElementsPerPage()));
    }

    @Getter
    @AllArgsConstructor
    public class Page {

        private final int number, begin, end;

        public List<T> getElements() {
            return Pagination.this.getElements().subList(getBegin(), Math.min(Pagination.this.countElements(), getEnd()));
        }

        public int countElements() {
            return getElements().size();
        }

        public boolean contains(T element) {
            return getElements().contains(element);
        }

    }

}