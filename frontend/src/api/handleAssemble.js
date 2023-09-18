import axios from "axios";

export const handleAssemble = async (
  assemblerProgram,
  setListing,
  setConsoleOutput
) => {
  const api = axios.create();
  try {
    const response = await api.post(
      "/api/assembler/assemble",
      assemblerProgram,
      {
        headers: {
          "Content-Type": "text/plain",
        },
      }
    );

    const responseText = response.request.responseText;
    console.log("Raw response text:", responseText);

    const responseData = JSON.parse(responseText);
    responseData.consoleOutput = console;
    responseData.listing = handleAssemble();
    console.log("Parsed response data:", responseData);

    if (responseData.error) {
      setConsoleOutput(responseData.error);
    } else {
      const machineCodeRegex =
        /^([0-9A-Fa-f]+)\s*:\s*([0-9A-Fa-f]+)(?:\s*;\s*([A-Za-z]+)\s*(\S+))?/gm;
      const formattedMachineCode = responseData.listing
        .replace(
          machineCodeRegex,
          (match, address, code, instruction, operand) => {
            const formattedOperand = operand ? `\t${operand}` : "";
            return `${address}\t\t: ${code}\t\t; ${instruction}${formattedOperand}`;
          }
        )
        .trim();

      setListing(formattedMachineCode);
      setConsoleOutput(responseData.consoleOutput);
      console.log("Assembly completed successfully!");
    }
  } catch (error) {
    console.error("Error occurs ", error);
    setConsoleOutput(`Error occurred during assembly: ${error.message}`);
  }
};
