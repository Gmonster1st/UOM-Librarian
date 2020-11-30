package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.model.publisher.Publisher;

public interface PublisherProtected {
    String getISBN();
    void setISBN(String isbn);
    Publisher getPublisher();
    void setPublisher(Publisher publisher);
}
