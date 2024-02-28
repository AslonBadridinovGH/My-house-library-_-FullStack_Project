import {Book} from "../types/Book.ts";
import {BookElement} from "./book-element.tsx";
import React from "react";
import styled from "styled-components";
import {FavoriteBook} from "../types/FavoriteBook.ts";

type ViewAllBooksProps = {
    books: Book[]
    onclickHeart: (book:Book)=>void
    favorites:FavoriteBook[]
}

export const ViewAllBooks: React.FC<ViewAllBooksProps> = ({books, onclickHeart,  favorites}) => {

    return (
        <StyledDiv>
            <h2>Meine Bücher</h2>
          <div className="books">
            {books.map(book => (<BookElement key={book.id} book={book}  onclickHeart={onclickHeart}
                                  isFavorite={favorites.find((favBook)=> book.id===favBook.book.id)!==undefined} />))}
          </div>
        </StyledDiv>
    );

}
const StyledDiv =styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
`;