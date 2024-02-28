import {Book} from "../types/Book.ts";
import React from "react";

import {FavoriteBook} from "../types/FavoriteBook.ts";
import {BookElement} from "./book-element.tsx";


type ViewFavoriteBooksProps = {
    books : Book[]
    onclickHeart : (book:Book) => void
    favorites : FavoriteBook[]
}

export const ViewFavoriteBooks: React.FC<ViewFavoriteBooksProps> = ({books, onclickHeart, favorites}) => {

    return (
        <div className="books">

            {favorites.map(favBook => (<BookElement key={favBook.id} book={favBook.book}
             onclickHeart={onclickHeart} isFavorite={books.find((book)=> book.id===favBook.book.id) !== undefined} />))}
        </div>
    );

}
