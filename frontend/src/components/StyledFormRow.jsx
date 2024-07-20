import styled from "styled-components";

const StyledFormRow = styled.div`
  display: grid;
  align-items: center;
  grid-template-columns: 0.7fr 1.5fr 1.2fr;
  gap: 2.4rem;

  padding: 1.2rem 0;

  &:first-child {
    padding-top: 0;
  }

  &:last-child {
    padding-bottom: 0;
  }

  //&:not(:last-child) {
  //  border-bottom: 1px solid #1f2937;
  //}

  &:has(button) {
    display: flex;
    justify-content: flex-end;
    gap: 1.2rem;
  }
`;

const Label = styled.label`
  font-weight: 500;
`;

const RequiredStar = styled.span`
  color: red;
`;

const Error = styled.span`
  font-size: 1.4rem;
  color: #b91c1c;
`;

function FormRow({ label, error, isRequired, children }) {
  return (
    <StyledFormRow>
      {label && (
        <Label htmlFor={children.props.id}>
          {label} {isRequired && <RequiredStar>*</RequiredStar>}
        </Label>
      )}
      {children}
      {error && <Error>{error}</Error>}
    </StyledFormRow>
  );
}

export default FormRow;
