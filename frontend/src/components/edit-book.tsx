import {Book} from "../types/Book.ts";
import React, {ChangeEvent, FormEvent, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {AxiosPromise} from "axios";

type EditBookProps = {
    books: Book[],
    editBook: (book: Book) => void
    onUpload: (file: File) => AxiosPromise
}

export const EditBook: React.FC<EditBookProps> = ({books, editBook, onUpload}) => {

    const {id} = useParams();

    const book: Book | undefined = books.find(book => book.id === id);

    const [title, setTitle] = useState<string>(book?.title || "")

    const [author, setAuthor] = useState<string>(book?.author || "")

    const [img, setImg] = useState<string>(book?.img || "")

    const [genre, setGenre] = useState<string>(book?.genre || "")

    const [year, setYears] = useState<number>(book?.year || 0)

    const [publisher, setPublisher] = useState<string>(book?.publisher || "")

    const [city, setCity] = useState<string>(book?.city || "")

    const [page, setPage] = useState<number>(book?.page || 0)

    const [description, setDescription] = useState<string>(book?.description || "")

    const [views, setViews] = useState<number>(book?.views || 0)

    const [file, setFile] = useState<File | null>(null);

    function handleChangeFile(event: ChangeEvent<HTMLInputElement>) {
        if (!event.target.files) {
            return;
        } else {
            setFile(event.target.files[0])
        }
    }

    const onTitleChange = (event: ChangeEvent<HTMLInputElement>): void => {
        setTitle(event.target.value)
    }

    const onAuthorChange = (event: ChangeEvent<HTMLInputElement>): void => {
        setAuthor(event.target.value)
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

    function onViewsChange(event: ChangeEvent<HTMLInputElement>) {
        setViews(event.target.valueAsNumber)
    }


    const onSubmitEdit = (event: FormEvent<HTMLFormElement>): void => {
        event.preventDefault()
        onUpload(file!).then((r) => {
            setImg(r.data)
            editBook({
                id: book?.id || "",
                title, author, img: r.data, genre, year, publisher, city, page, description, views
            })
        }).catch((e) => {
            throw (e)
        });
    }

    const navigate = useNavigate();
    const redirect = ()=>{
        navigate("/list")
    }

    return (
        <div>
            <div>
                <form onSubmit={onSubmitEdit}>
                    <div>
                        <div>Title</div>
                        <input name="title" value={title} type="text" onChange={onTitleChange} placeholder="Title..."/>
                    </div>
                    <div>
                        <div>Author</div>
                        <input name="author" value={author} type="text" onChange={onAuthorChange}
                               placeholder="Author..."/>
                    </div>
                    <div>
                        <div>Image</div>
                        <input type="file" onChange={handleChangeFile}/>
                        {file ? <img src={URL.createObjectURL(file)} alt={"Bild"} width="auto" height="300vw"/> : img ?
                            <img src={img} alt={`${title} book cover`} width="auto" height="300vw"/> : null}
                    </div>
                    <div>
                        <div>Genre</div>
                        <input name={"genre"} value={genre} type={"text"} onChange={onGenreChange}
                               placeholder="Genre..."/>
                    </div>
                    <div>
                        <div>Year</div>
                        <input name={"year"} value={year} type={"number"} onChange={onYearChange}
                               placeholder="Year..."/>
                    </div>
                    <div>
                        <div>Publisher</div>
                        <input name={"publisher"} value={publisher} onChange={onPublisherChange}
                               placeholder="Publisher..."/>
                    </div>
                    <div>
                        <div>City</div>
                        <input name={"city"} value={city} onChange={onCityChange} placeholder="City..."/>
                    </div>
                    <div>
                        <div>Page</div>
                        <input name={"page"} value={page} type={"number"} onChange={onPageChange}
                               placeholder="Page..."/>
                    </div>
                    <div>
                        <div>Description</div>
                        <input name={"description"} value={description} onChange={onDescriptionChange}
                               placeholder="Description..."/>
                    </div>
                    <div>
                        <div>Views</div>
                        <input name={"views"} value={views} type={"number"} onChange={onViewsChange}
                               placeholder="Views..."/>
                    </div>
                    <button type="submit">Edit book</button>
                    <button onClick={redirect}>List Book</button>
                </form>
            </div>
        </div>
    );

}