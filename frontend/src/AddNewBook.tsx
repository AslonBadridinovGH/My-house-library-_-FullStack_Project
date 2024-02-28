import {Book} from "./types/Book.ts";
import {ChangeEvent, FormEvent, useState} from "react";
import {AxiosPromise} from "axios";
import styled from "styled-components";


type AddNewBookProps = {
    saveBook: (bookToSave: Book) => void
    onUpload: (file:File) => AxiosPromise
}


export default function AddNewBook(props: AddNewBookProps) {

    const [title, setTitle] = useState<string>("")

    const [author, setAuthor] = useState<string>("")

    const [genre, setGenre] = useState<string>("")

    const [year, setYears] = useState<number>(2024)

    const [publisher, setPublisher] = useState<string>("")

    const [city, setCity] = useState<string>("")

    const [page, setPage] = useState<number>(0)

    const [description, setDescription] = useState<string>("")

    const [views, setViews] = useState<number>(0)

    const [file, setFile] = useState<File|null>(null);


    function handleChangeFile(event: ChangeEvent<HTMLInputElement>){
        if(!event.target.files){return;
        }else{
            setFile(event.target.files[0])
        }
    }

    function onTitleChange(event: ChangeEvent<HTMLInputElement>) {
        setTitle(event.target.value);
    }

    function onAuthorChange(event: ChangeEvent<HTMLInputElement>) {
        setAuthor(event.target.value);
    }

    function onGenreChange(event: ChangeEvent<HTMLInputElement>) {
        setGenre(event.target.value)
    }

    function onYearChange(event: ChangeEvent<HTMLInputElement>) {
        setYears(event.target.valueAsNumber);
    }

    function onPublisherChange(event: ChangeEvent<HTMLInputElement>) {
        setPublisher(event.target.value)
    }

    function onCityChange(event: ChangeEvent<HTMLInputElement>) {
        setCity(event.target.value);
    }

    function onPageChange(event: ChangeEvent<HTMLInputElement>) {
        setPage(event.target.valueAsNumber)
    }

    function onDescriptionChange(event: ChangeEvent<HTMLInputElement>) {
        setDescription(event.target.value)
    }

    const onBookSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        props.onUpload(file!).then((r) => {
                console.log(r);

            const bookToSave: Book = {
                id: "1",
                title: title,
                author: author,
                img: r.data,
                genre: genre,
                year: year,
                publisher: publisher,
                city: city,
                page: page,
                description: description,
                views: views
            }
            props.saveBook(bookToSave)

            setTitle("")
            setAuthor("")
            setGenre("")
            setYears(2024)
            setPublisher("")
            setCity("")
            setPage(0)
            setDescription("")
            setViews(0)
        })
            .catch((e)=> {
                throw(e)});
    }

    return (
        <StyledDiv>

            <p>Add Book</p>
            <StyledFormAdd onSubmit={onBookSubmit}>

                <SInput value={title} onChange={onTitleChange} placeholder="Title"/>
                <SInput value={author} onChange={onAuthorChange} placeholder={"Author"}/>

                    <SInput type="file" onChange={handleChangeFile}/>
                    {file ? <img src={URL.createObjectURL(file)} alt={"Bild"} width="auto" height="300vw"/>:null}

                <SInput value={genre} onChange={onGenreChange} placeholder={"Genre"}/>
                <SInput value={year} type={"number"} onChange={onYearChange} placeholder={"Year"}/>
                <SInput value={publisher} onChange={onPublisherChange} placeholder={"Publisher"}/>
                <SInput value={city} onChange={onCityChange} placeholder={"City"}/>
                <SInput value={page} type={"number"} onChange={onPageChange} placeholder={"Page"}/>
                <SInput value={description} onChange={onDescriptionChange} placeholder={"Description"}/>

                <button type="submit">Save</button>

            </StyledFormAdd>
        </StyledDiv>
    )

}
const SInput =styled.input`
margin: 0.5vw 0 0.5vw 0`;

const StyledFormAdd = styled.form`
    display:flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: initial;
    width:60%;
`;

const StyledDiv = styled.div`
    display: flex;
    padding: 3vw;
    flex-direction: column;
`;