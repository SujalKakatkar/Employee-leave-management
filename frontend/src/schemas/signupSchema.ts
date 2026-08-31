import { z } from "zod";

export const signupSchema = z
    .object({
        username: z
            .string()
            .min(2, "username must be at least 2 characters")
            .regex(/^[a-zA-Z0-9_]+$/, 'Username can only contain letters, numbers, and underscores')
            .max(20),

        email: z
            .string()
            .email("Invalid email address"),

        password: z
            .string()
            .min(6, "Password must be at least 6 characters")
            .max(100),

        confirmPassword: z
            .string(),
    })
    .refine((data) => data.password === data.confirmPassword, {
        message: "Passwords do not match",
        path: ["confirmPassword"],
    });

export type SignupSchemaType = z.infer<typeof signupSchema>;