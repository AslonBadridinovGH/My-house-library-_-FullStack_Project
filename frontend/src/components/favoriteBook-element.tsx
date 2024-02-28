/*
import {Book} from "../types/Book.ts";
import React from "react";
import {useNavigate} from "react-router-dom";
import { CiHeart } from "react-icons/ci";
import styled from "styled-components";
import {FavoriteBook} from "../types/FavoriteBook.ts";

type BookElementProps = {
    favoriteBook: FavoriteBook
    onclickHeart: (book:Book)=>void
    isFavorite:boolean
}

export const FavoriteBookElement: React.FC<BookElementProps> = ({favoriteBook, onclickHeart, isFavorite}) => {

    const navitage= useNavigate();

    const onboxclick=()=>{
        navitage(`/books/${favoriteBook.book.id}`)
    }

    const onheartClick=(event :React.MouseEvent <HTMLDivElement, MouseEvent>)=>{
        event.stopPropagation()
        onclickHeart(favoriteBook.book)
    }

    return (

        <div onClick={onboxclick} className="book">

            <StyledDiv>

                <StyledH>{favoriteBook.book.title}</StyledH>

                <StyledImg src={favoriteBook.book?.img} alt={`${favoriteBook.book?.title} book cover`}/>

                <div className={isFavorite ? "heartIcon-active" : "heartIcon"} onClick={onheartClick}>
                    <CiHeart/>
                </div>

            </StyledDiv>

        </div>

    )
        ;

}
const StyledH = styled.h2`
    margin: 1vw 0 0 0;
    font-size: 2vw;
    font-style: normal`;

const StyledDiv = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    box-shadow: 0 2px 4px 0 rgba(38, 59, 56, 0.10), 0 0 0 1.5px rgba(38, 50, 56, 0.10);
    margin: .25rem;
    height: 18rem;
    width: 20rem;
    border-radius: 0.375rem;
    border-color: rgb(221 221 221);
    background-color: white;
    padding: 1.25rem;
`;


const StyledImg = styled.img`
    margin: 1vw 0 1vw 0;
    height: auto;
    width: auto;
    max-width: 90%;
    max-height: 90%;
    object-fit: contain`;*/
