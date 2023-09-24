import axios from "axios";

export const handleLoadFile = async (setAssemblerProgram, setConsoleOutput) => {
  const api = axios.create();
  const fileInput = document.createElement("input");

  fileInput.type = "file";
  fileInput.accept = ".txt";
  fileInput.click();

  fileInput.addEventListener("change", async (event) => {
    const selectedFile = event.target.files[0];

    if (!selectedFile) {
      console.error("No file selected.");
      setConsoleOutput("No file selected.");
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFile);

    try {
      const response = await api.post("/api/assembler/loadFile", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      setAssemblerProgram(response.data);
      console.log("File content:", response.data);
    } catch (error) {
      console.error("Error occurred while uploading the file:", error);
      setConsoleOutput(
          `Error occurred while loading the file: ${error.message}`
      );
    }
  });
};
