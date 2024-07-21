import styled from "styled-components";

const Cover = styled.div`
    min-width: fit-content;
    display: flex;
    flex-direction: row;
    justify-content: center;
    font-size: medium;
    text-align: left;
`

const Row = styled.div`
    padding: 5px;
`

const RowBold = styled.div`
    font-weight: bold;
    padding: 5px;
`

function DisplayInfo({label, value}) {
    return <Cover>
        <Row>{label}</Row>
        <RowBold>{value}</RowBold>
    </Cover>
}

export default DisplayInfo;