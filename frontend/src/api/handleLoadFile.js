import axios from "axios";

export const handleLoadFile = async (setAssemblerProgram, setConsoleOutput) => {
  const api = axios.create();

  try {
    const response = await api.get("/api/assembler/loadFile");
    setAssemblerProgram(response.data);
    console.log("File content:", response.data);
  } catch (error) {
    console.error("Error occurs ", error);
    setConsoleOutput(`Error occurred while loading the file: ${error.message}`);
  }
};
