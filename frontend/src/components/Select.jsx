import styled from "styled-components";

const StyledSelect = styled.select`
  font-size: 1.4rem;
  padding: 0.7rem 1.2rem;
  border: 1px solid #4b5563;
  border-radius: 5px;
  background-color: #fff;
  font-weight: 400;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.04);
`;

function Select({ options, value, onChange, ...props }) {
    return (
        <StyledSelect value={value} onChange={onChange} {...props}>
            {options.map((option) => (
                <option value={option.value} key={option.value}>
                    {option.label}
                </option>
            ))}
        </StyledSelect>
    );
}

export default Select;
