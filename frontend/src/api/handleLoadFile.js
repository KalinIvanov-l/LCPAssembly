import axios from "axios";

export const createFileInput = () => {
  const fileInput = document.createElement("input");
  fileInput.type = "file";
  fileInput.accept = ".txt";
  return fileInput;
};

export const handleLoadFile = async (setAssemblerProgram, setConsoleOutput) => {
  const fileInput = createFileInput();
  fileInput.click();

  const onFileChange = async (event) => {
    const selectedFile = event.target.files[0];
    fileInput.removeEventListener("change", onFileChange);

    if (!selectedFile) {
      throw new Error("No file selected. Please select a file.");
    }

    const formData = new FormData();
    formData.append("file", selectedFile);
    const api = axios.create();

    try {
      const response = await api.post("/api/assembler/loadFile", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      setAssemblerProgram(response.data);
      setConsoleOutput("File successfully loaded.");
    } catch (error) {
      console.error("Error occurred while uploading the file:", error);
      setConsoleOutput(`Error occurred while loading the file: ${error.message}`);
    }
  };

  fileInput.addEventListener("change", onFileChange);
};